//preview.js
//获取应用实例
import { set as setGlobalData, get as getGlobalData } from '../../global_data'
import { API_STORYBOOK_ID_PAGES, API_STORYPAGE_ID_VIEWS } from '../../constants/api-endpoints'

var app = getApp();
Page(
{
data: {
    bookPages: [],
    indicatorDots: true,
    autoplay: false,
    interval: 5000,
    duration: 1000,
    maxWidth: 0,
    maxHeight: 0,
    rMaxWidth:0,
    rMaxHeight:0
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
,aspectRatioDisplay(){
    //@see: https://eikhart.com/blog/aspect-ratio-calculator
    var bookPage = this.data.bookPages[0];
    console.log("bookPage:",bookPage);
    var rawImgWidth = bookPage.width;
    var rawImgHeight = bookPage.height;
    var aspectRatio = ( rawImgWidth / rawImgHeight );
    console.log("rawImgWidth:",rawImgWidth,"rawImgHeight:",rawImgHeight,",aspectRatio:",aspectRatio);
    //by height default 
    // var resizedHeight = (rawImgHeight>this.data.maxHeight)?this.data.maxHeight:rawImgHeight;
    var resizedHeight = this.data.maxHeight;
    var resizedWidth = resizedHeight / aspectRatio;
    var scaleX = resizedWidth/rawImgWidth;
    var scaleY = resizedHeight/rawImgHeight;
    console.log("by height, resizedWidth:",resizedWidth,"resizedHeight:",resizedHeight,",scaledX:",scaleY,",scaledY:",scaleY);
    //by width
    // var resizedWidth = (rawImgWidth>this.data.maxWidth)?this.data.maxWidth:rawImgWidth;
    // var resizedHeight = resizedWidth * aspectRatio;
    // resizedHeight = resizedHeight>this.data.maxHeight?this.data.maxHeight:resizedHeight;
    // var scaleX = resizedWidth/rawImgWidth;
    // var scaleY = resizedHeight/rawImgHeight;
    //if two much width, too much height;
    // console.log("by width, resizedWidth:",resizedWidth,"resizedHeight:",resizedHeight,",scaledX:",scaleY,",scaledY:",scaleY);
    //
    this.setData({
            rMaxWidth: resizedWidth,
            rMaxHeight: resizedHeight
    });
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
        _this.setData({
            maxWidth: maxCanvasWidth,
            maxHeight: maxCanvasHeight
        });
        //
        _this.aspectRatioDisplay();
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
    
}

})