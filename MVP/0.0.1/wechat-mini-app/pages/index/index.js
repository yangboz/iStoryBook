//Index.js
//获取应用实例
import { set as setGlobalData, get as getGlobalData } from '../../global_data'
import { API_STORYBOOK_PUBLIC } from '../../constants/api-endpoints' 

var app = getApp();
Page({
  data: {
    bookList: []
  }
,created (options) {
    console.log(options,option.query)
    // Do some initialize when page load.
  },
onReady () {
// console.log(this.selectComponent())
// Do something when page ready.
// console.log("result router:",this);
//global variables
},
onLoad: function(option){
      console.log("@IndexPage,onLoad:",option);
      //https://www.jianshu.com/p/2057d88f0920
      //get public storybook pages
  var _this = this;
    wx.request({
      url: API_STORYBOOK_PUBLIC,
      data: {},
      method: 'GET',
      success: function (res) {
        console.log(API_STORYBOOK_PUBLIC + " success resp:", res);
        //
        _this.setData({ bookList:res.data});
      }
      ,fail: function (err) {
        console.error(API_STORYBOOK_PUBLIC + " fail:", err);
      }
    });
}
,showBookDetail: function (e) {
    let id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/book_detail/?id=' + id,
    })
  }
,onImagePickerBtnClick: function(e){
	var _this = this;
	wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album','camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths;
        console.log("onImagePickerBtnClick res:",res,",tempFiles:",res.tempFiles);
        //update the values of withImages
      	setGlobalData("withImages",tempFilePaths);
      	//
      	_this.imageUploadHandler();
      },
      fail:function(error){
      	console.error(error);
      }
    })
 }   
,imageUploadHandler: function(){
	  var _this = this;
    console.log("ENDPOINT_IMAGE_UPLOAD:",ENDPOINT_IMAGE_UPLOAD);
    //
    let filePath = getGlobalData("withImages")[0];
    console.log("getGlobalData(withImages):",getGlobalData("withImages"),"[0]:",filePath);
    // File that needs to be uploaded.
    // https://developers.weixin.qq.com/miniprogram/dev/api/network-file.html#wxuploadfileobject
    let uploadParam = {
      url: ENDPOINT_IMAGE_UPLOAD,
      // url: "http://101.201.152.97:5001/pics/pics-automatic-review",
      filePath: filePath,
      name: "file",
      header:{
        'content-type': 'application/json' // 默认值
      }
    }
    //
     wx.uploadFile(
     {
        url: ENDPOINT_IMAGE_UPLOAD,
        // url: "http://101.201.152.97:5001/pics/pics-automatic-review",
        filePath: filePath,
        name: "file",
        header:{
          'content-type': 'application/json' // 默认值
        },
        success: function (response) {
          console.log("uploadTask success.");
          _this.uplodTaskCompleteHandler(response);
        },
        fail: function(error){
        	console.error("uploadTask failed,error:",error);
        }
      }
    );
     
  }
  ,uplodTaskCompleteHandler(response){
    wx.hideLoading();
   }
})