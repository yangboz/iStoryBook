import { set as setGlobalData, get as getGlobalData } from '../../global_data'
import { API_STORYBOOK_MY } from '../../constants/api-endpoints'

Page({
  data: {
    bookList: []
  }
  ,onLoad: function (option) {
    console.log("@MyPage,onLoad:", option);
    var g_openid = getGlobalData("openid");
    var _this = this;
    //get my storybook pages
    wx.request({
      url: API_STORYBOOK_MY + g_openid,
      data: {},
      method: 'GET',
      success: function (res) {
        console.log(API_STORYBOOK_MY + " success resp:", res);
        //
        _this.setData({ bookList: res.data });
      }
      , fail: function (err) {
        console.error(API_STORYBOOK_MY + " fail:", err);
      }
    });
  }
  ,onShow() {
    
  },
  onHide() {
    
  },
  onUnload() {
  }
  
})