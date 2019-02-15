//preview.js
//获取应用实例
import { set as setGlobalData, get as getGlobalData } from '../../global_data'
import { API_STORYBOOK_ID_PAGES } from '../../constants/api-endpoints'

var app = getApp();
Page(
{
data: {
    bookPages: [],
    imgUrls: [],
    indicatorDots: true,
    autoplay: false,
    interval: 5000,
    duration: 1000
  }
,changeIndicatorDots(e) {
    this.setData({
      indicatorDots: !this.data.indicatorDots
    })
  }
  ,changeAutoplay(e) {
    this.setData({
      autoplay: !this.data.autoplay
    })
  }
  ,intervalChange(e) {
    this.setData({
      interval: e.detail.value
    })
  }
  ,durationChange(e) {
    this.setData({
      duration: e.detail.value
    })
  }
,created (options) {
    console.log(options,option.query)
    // Do some initialize when page load.
}
,onReady () {
    var _this = this;
// console.log(this.selectComponent())
// Do something when page ready.
// console.log("result router:",this);
    wx.getSystemInfo({
      success: function(res) {
        // rpx = res.windowWidth/375;
        // console.log("getSystemInfo:",res,",rpx:",rpx);
        var maxCanvasWidth = res.windowWidth;// - 56;
        var maxCanvasHeight = res.windowHeight;// - 200;
        console.log("maxCanvasWidth:",maxCanvasWidth,",maxCanvasHeight:",maxCanvasHeight);
        // _this.setData({
        //     maxWidth: maxCanvasWidth,
        //     maxHeight: maxCanvasHeight
        // });
      }
    });
}
,onLoad: function(option){
    console.log("onLoad:",option);
    //get API_STORYBOOK_ID_PAGES
    var _this = this;
    wx.request({
      url: API_STORYBOOK_ID_PAGES+option.id+"/pages/",
      data: {},
      method: 'GET',
      success: function (res) {
        console.log(API_STORYBOOK_ID_PAGES + option.id + " success resp:", res);
        //
        _this.setData({ bookPages: res.data });
      }
      , fail: function (err) {
        console.error(API_STORYBOOK_ID_PAGES + " fail:", err);
      }
    });
    // let global_imagePreview = getGlobalData('imagePreview');
    // console.log("@PreviewPage, transfered global_imagePreview:",global_imagePreview);
    // this.setData({imagePreview: global_imagePreview.filePath});
    // // go to imagedReviewHandler
    // this.imagedReviewHandler(global_imagePreview.fileName);
    
}

})