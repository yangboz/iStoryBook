//preview.js
//获取应用实例
import { set as setGlobalData, get as getGlobalData } from '../../global_data'
import {  } from '../../constants/api-endpoints'

var app = getApp();
Page(
{
data: {
    imgUrls: [
      'http://knighter.xyz:82/assets/templates/1.jpg',
      'http://knighter.xyz:82/assets/templates/2.jpg',
      'http://knighter.xyz:82/assets/templates/3.jpg',
      'http://knighter.xyz:82/assets/templates/4.jpg'
    ],
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
,aspectRatioDisplay(maxWidth,maxHeight){
    var global_imagePreview = getGlobalData("imagePreview");
    console.log("global_imagePreview:",global_imagePreview);
    //@see: https://eikhart.com/blog/aspect-ratio-calculator
    var rawImgWidth = global_imagePreview.width;
    var rawImgHeight = global_imagePreview.height;
    var aspectRatio = ( rawImgWidth / rawImgHeight );
    console.log("rawImgWidth:",rawImgWidth,"rawImgHeight:",rawImgHeight,",aspectRatio:",aspectRatio);
    //by height default 
    // var resizedWidth = (rawImgWidth>maxCanvasWidth)?maxCanvasWidth:rawImgWidth;
    var resizedHeight = (rawImgHeight>maxHeight)?maxHeight:rawImgHeight;
    var resizedWidth = resizedHeight * aspectRatio;
    var scaleX = resizedWidth/rawImgWidth;
    var scaleY = resizedHeight/rawImgHeight;
    //if two much width, too much height;
    console.log("resizedWidth:",resizedWidth,"resizedHeight:",resizedHeight,",scaledX:",scaleY,",scaledY:",scaleY);
    //
    this.setData({
            rWidth: resizedWidth,
            rHeight: resizedHeight
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
        _this.aspectRatioDisplay(maxCanvasWidth,maxCanvasHeight);
      }
    });
}
,onLoad: function(option){
    console.log("onLoad:",option);
    //
    let global_imagePreview = getGlobalData('imagePreview');
    console.log("@PreviewPage, transfered global_imagePreview:",global_imagePreview);
    this.setData({imagePreview: global_imagePreview.filePath});
    // go to imagedReviewHandler
    this.imagedReviewHandler(global_imagePreview.fileName);
    
}
,reviewTaskCompleteHandler(response){
    console.log("reviewTaskCompleteHandler.response:",response);
//
    console.log("response.data:",response.data);
    var jsonData = response.data.data;
    console.log("response.data.data:",jsonData);
    //transfer to Result page.
    setGlobalData('imageResult',jsonData);
//
    // var jsonDataImage = jsonData.image;
    // console.log("response.data.data.image:",jsonDataImage);
    // this.setState({withResults: undefined!=jsonDataImage});
    // setGlobalData('imageResult',jsonDataImage);
    //
  // 跳转到目的页面，打开新页面
  // wx.navigateTo({
  //   // url: '/pages/results/result?image='+this.state.withResults
  //   url: '/pages/results/result'
  // })
  wx.redirectTo({
    // url: '/pages/results/result?image='+this.state.withResults
    url: '/pages/results/result'
  })
  //clear the cache of src image.
  setGlobalData('imageHistory',null);
  console.log("!global_imageHistory reset start!");
  wx.hideToast();
}
  ,imagedReviewHandler(image_file_name){
    var _this = this;
    wx.showToast({title: '判题中...',icon: 'loading'});
//Notice:https://zhuanlan.zhihu.com/p/26991549
    wx.request({
        url: ENDPOINT_IMAGE_REVIEW,
        data: {
          file_name: image_file_name
        },
        method: 'POST',
        dataType: 'json',
        header: {
          'content-type': 'application/x-www-form-urlencoded'
        }
        ,success: function(res){
            // console.log('imagedReviewHandler response:',res);
            _this.reviewTaskCompleteHandler(res);
        }
      });
    }
})