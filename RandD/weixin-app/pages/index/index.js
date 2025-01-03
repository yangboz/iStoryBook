/*
 * 
 * WordPres版微信小程序
 * author: jianbo
 * organization: 守望轩  www.watch-life.net
 * github:    https://github.com/iamxjb/winxin-app-watch-life.net
 * 技术支持微信号：iamxjb
 * 开源协议：MIT
 * 
 *  *Copyright (c) 2017 https://www.watch-life.net All rights reserved.
 */

var Api = require('../../utils/api.js');
var util = require('../../utils/util.js');
var WxParse = require('../../wxParse/wxParse.js');
var wxApi = require('../../utils/wxApi.js')
var wxRequest = require('../../utils/wxRequest.js')
var that;
import config from '../../utils/config.js'

var pageCount = config.getPageCount;

Page({
  data: {    
    postsList: [],
    postsShowSwiperList:[],
    isLastPage:false,    
    page: 1,
    search: '',
    categories: 0,
    showerror:"none",
    showCategoryName:"",
    categoryName:"",
    showallDisplay:"block", 
    displayHeader:"none",
    displaySwiper: "none",
    floatDisplay: "none",
    displayfirstSwiper:"none",
    topNav: []
    

  },
  formSubmit: function (e) {
    var url = '../list/list'
    var key ='';
    if (e.currentTarget.id =="search-input")
    {
        key = e.detail.value;
    }
    else{

        key = e.detail.value.input;

    }
    if (key != '') {
      url = url + '?search=' +key;
      wx.navigateTo({
        url: url
      })
    }
    else
    {
      wx.showModal({
        title: '提示',
        content: '请输入内容',
        showCancel: false,
      });


    }
  },
  onShareAppMessage: function () {
    return {
      title: '“' + config.getWebsiteName+'”网站微信小程序,基于WordPress版小程序构建.',
      path: 'pages/index/index',
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },
  onPullDownRefresh: function () {
    var self = this;
    self.setData({
      showerror: "none",
      showallDisplay:"none",
      displaySwiper:"none",
      floatDisplay:"none",
      isLastPage:false,
      page:0,
      postsShowSwiperList:[]
    });
    this.fetchTopFivePosts(); 
    
  },
  onReachBottom: function () {  
   
  },
  onLoad: function (options) {
    var self = this; 
    this.fetchTopFivePosts();
    self.setData({
        topNav: config.getIndexNav

    });
       
  },
  onShow: function (options){
      wx.setStorageSync('openLinkCount', 0);

  },  
  fetchTopFivePosts: function () {
    var self = this;
    //取置顶的文章
    var getPostsRequest = wxRequest.getRequest(Api.getSwiperPosts());
    getPostsRequest.then(response => {
        if (response.data.status =='200' && response.data.posts.length > 0) {
                self.setData({
                    postsShowSwiperList: response.data.posts,
                    postsShowSwiperList: self.data.postsShowSwiperList.concat(response.data.posts.map(function (item) {
                        //item.firstImage = Api.getContentFirstImage(item.content.rendered);
                        if (item.post_medium_image_300 == null || item.post_medium_image_300 == '') {
                            if (item.content_first_image != null && item.content_first_image != '') {
                                item.post_medium_image_300 = item.content_first_image;
                            }
                            else {
                                item.post_medium_image_300 = "../../images/logo700.png";
                            }

                        }
                        return item;
                    })),
                    showallDisplay: "block",
                    displaySwiper: "block"
                });
                
            }
            else {
                self.setData({
                    displaySwiper: "none",
                    displayHeader: "block",
                    showallDisplay: "block",

                });
                
            }
     
    })
        .then(response=>{
            self.fetchPostsData(self.data);

        })
        .catch(function (response){
            console.log(response); 
            self.setData({
                showerror: "block",
                floatDisplay: "none"
            });

        })
        .finally(function () {
        
    });            
   
  },

  //获取文章列表数据
  fetchPostsData: function (data) {
    var self = this;    
    if (!data) data = {};
    if (!data.page) data.page = 1;
    if (!data.categories) data.categories = 0;
    if (!data.search) data.search = '';
    if (data.page === 1) {
      self.setData({
        postsList: []
      });
    };
    wx.showLoading({
      title: '正在加载',
      mask:true
    }); 
    var getPostsRequest = wxRequest.getRequest(Api.getPosts(data));
    getPostsRequest
        .then(response => {
            if (response.statusCode === 200) {

                if (response.data.length < pageCount) {
                    self.setData({
                        isLastPage: true
                    });
                }
                self.setData({
                    floatDisplay: "block",
                    postsList: self.data.postsList.concat(response.data.map(function (item) {

                        var strdate = item.date
                        if (item.category_name != null) {

                            item.categoryImage = "../../images/category.png";
                        }
                        else {
                            item.categoryImage = "";
                        }

                        if (item.post_thumbnail_image == null || item.post_thumbnail_image == '') {
                            item.post_thumbnail_image = "../../images/logo700.png";
                        }
                        item.date = util.cutstr(strdate, 10, 1);
                        return item;
                    })),

                });
                setTimeout(function () {
                    wx.hideLoading();
                }, 900);
            }
            else {
                if (response.data.code == "rest_post_invalid_page_number") {
                    self.setData({
                        isLastPage: true
                    });
                    wx.showToast({
                        title: '没有更多内容',
                        mask: false,
                        duration: 1500
                    });
                }
                else {
                    wx.showToast({
                        title: response.data.message,
                        duration: 1500
                    })
                }
            }


        })
        .catch(function (response)
        {
            if (data.page == 1) {

                self.setData({
                    showerror: "block",
                    floatDisplay: "none"
                });

            }
            else {
                wx.showModal({
                    title: '加载失败',
                    content: '加载数据失败,请重试.',
                    showCancel: false,
                });
                self.setData({
                    page: data.page - 1
                });
            }

        })
        .finally(function (response) {
            wx.hideLoading();
            wx.stopPullDownRefresh();
        });
  },
  //加载分页
  loadMore: function (e) {
    
    var self = this;
    if (!self.data.isLastPage)
    {
      self.setData({
        page: self.data.page + 1
      });
      //console.log('当前页' + self.data.page);
      this.fetchPostsData(self.data);
    }
    else
    {
      wx.showToast({
        title: '没有更多内容',
        mask: false,
        duration: 1000
      });
    }
  },
  // 跳转至查看文章详情
  redictDetail: function (e) {
    // console.log('查看文章');
    var id = e.currentTarget.id,
      url = '../detail/detail?id=' + id;
    wx.navigateTo({
      url: url
    })
  },
    forkDetail: function (e) {
        console.log('forkDetail');
        //
    },
  //首页图标跳转
  onNavRedirect:function(e){      
      var redicttype = e.currentTarget.dataset.redicttype;
      var url = e.currentTarget.dataset.url == null ? '' : e.currentTarget.dataset.url;
      var appid = e.currentTarget.dataset.appid == null ? '' : e.currentTarget.dataset.appid;
      var extraData = e.currentTarget.dataset.extraData == null ? '' : e.currentTarget.dataset.extraData;
      if (redicttype == 'apppage') {//跳转到小程序内部页面         
          wx.navigateTo({
              url: url
          })
      }
      else if (redicttype == 'webpage')//跳转到web-view内嵌的页面
      {
          url = '../webpage/webpage?url=' + url;
          wx.navigateTo({
              url: url
          })
      }
      else if (redicttype == 'miniapp')//跳转到其他app
      {
          wx.navigateToMiniProgram({
              appId: appid,
              envVersion: 'release',
              path: url,
              extraData: extraData,
              success(res) {
                  // 打开成功
              },
              fail: function (res) {
                  console.log(res);
              }
          })
      }
      
  },
  // 跳转至查看小程序列表页面或文章详情页
  redictAppDetail: function (e) {
      // console.log('查看文章');
      var id = e.currentTarget.id;
      var redicttype = e.currentTarget.dataset.redicttype;
      var url = e.currentTarget.dataset.url == null ? '':e.currentTarget.dataset.url;
      var appid = e.currentTarget.dataset.appid == null ? '' : e.currentTarget.dataset.appid;
      
      if (redicttype == 'detailpage')//跳转到内容页
      {
          url = '../detail/detail?id=' + id;
          wx.navigateTo({
              url: url
          })
      }
      if (redicttype == 'apppage') {//跳转到小程序内部页面         
          wx.navigateTo({
              url: url
          })
      }
      else if (redicttype == 'webpage')//跳转到web-view内嵌的页面
      {
          url = '../webpage/webpage?url=' + url;
          wx.navigateTo({
              url: url
          })          
      }
      else if (redicttype == 'miniapp')//跳转到其他app
      {
          wx.navigateToMiniProgram({
              appId: appid,
              envVersion: 'release',
              path: '',
              success(res) {
                  // 打开成功
              },
              fail: function (res) {
                  console.log(res);
              }
          })
      }
  },
  //返回首页
  redictHome: function (e) {
    //console.log('查看某类别下的文章');  
    var id = e.currentTarget.dataset.id,
      url = '/pages/index/index';
    wx.switchTab({
      url: url
    });
  },
  forkStorybook: function (e) {
    wx.showLoading({
      title: 'forking...',
    })
    //TODO:call forkStorybook api.
    that = this;
    var pid = 0;
    var uid = 0;
      wxApi.request({
      url: "http://localhost:8080/storybook/product/fork/"+pid+"/"+uid,
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      method: "POST",
      //data: { cityname: "上海", key: "1430ec127e097e1113259c5e1be1ba70" },
      data: util.json2Form({ cityname: "上海", key: "1430ec127e097e1113259c5e1be1ba70" }),
      complete: function (res) {
        that.setData({
          toastHidden: false,
          toastText: res.data,
          city_name: res,
          date: res,
          info: res,
        });
        if (res == null || res.data == null) {
          console.error('网络请求失败');
          return;
        }
      }
    })
  },
  onToastChanged: function () {
    that.setData({ toastHidden: true });
  }
})
