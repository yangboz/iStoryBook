import { set as setGlobalData, get as getGlobalData } from '../../global_data'

Page({

 onLoginBtnClick: function(e){

  // 必须是在用户已经授权的情况下调用
wx.getUserInfo({
  success(res) {
    const userInfo = res.userInfo
    const nickName = userInfo.nickName
    const avatarUrl = userInfo.avatarUrl
    const gender = userInfo.gender // 性别 0：未知、1：男、2：女
    const province = userInfo.province
    const city = userInfo.city
    const country = userInfo.country
  }
})

   // wx.showModal({
   //   title: 'Share WeChat info',
   //   content: 'Share WeChat info with ANIRAC.',
   //   showCancel: true,
   //   confirmColor: '#30b1f3',
   //   cancelText: 'No',
   //   confirmText: 'Yes',
   //   success: function (res) {
   //     if (res.confirm) {
         
   //       wx.redirectTo({
   //         url: '/pages/profile/profile'
   //       })

   //     }
   //     if (res.cancel) {
         
   //      //  wx.redirectTo({
   //      //    url: '/pages/index/index'
   //      //  })
   //     }

   //   }
   // });


	console.log("getWxCode...");
	var _this = this;
	// wx.navigateTo({url: '/pages/login/login'});
    wx.login({
      success: function (res) {
      	console.log("wx.login success response:",res);
        var appId = 'wxb91ed27f01c09f0d';//appid iStorybook
        var appSecret = '4caa1c4c4d0d9f05706816570e1e462c';//的app secret
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