import { set as setGlobalData, get as getGlobalData } from '../../global_data'

Page({

 onLoginBtnClick: function(e){

   wx.showModal({
     title: 'Share WeChat info',
     content: 'Share WeChat info with ANIRAC.',
     showCancel: true,
     confirmColor: '#30b1f3',
     cancelText: 'No',
     confirmText: 'Yes',
     success: function (res) {
       if (res.confirm) {
         
         wx.redirectTo({
           url: '/pages/profile/profile'
         })

       }
       if (res.cancel) {
         
        //  wx.redirectTo({
        //    url: '/pages/index/index'
        //  })
       }

     }
   });


	console.log("getWxCode...");
	var _this = this;
	// wx.navigateTo({url: '/pages/login/login'});
    wx.login({
      success: function (res) {
      	console.log("wx.login success response:",res);
        var appId = 'wx8834117a4fca9256';//appid
        var appSecret = '4a4a8e0d067b0ddeb027a451ec76a4af';//的app secret
        var js_code = res.code;//code
        console.log("js_code:",js_code);
        //only works for debugging
        wx.request({
          url: 'https://api.weixin.qq.com/sns/jscode2session?appid=' + appId + '&secret=' + appSecret + '&js_code=' + js_code + '&grant_type=authorization_code',
          data: {},
          method: 'GET',
          success: function (res) {
            var openid = res.data.openid //返回的用户唯一标识符openid
            console.log("openid:",openid);
          }
        });
        
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