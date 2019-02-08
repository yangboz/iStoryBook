import { set as setGlobalData, get as getGlobalData } from '../../global_data'
import { API_WX_USER_LOGIN, API_WX_USER_SAVE } from '../../constants/api-endpoints' ;


Page({
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  }
  ,onLoad: function () {
    // 查看是否授权
    wx.getSetting({
      success: function (res) {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称
          wx.getUserInfo({
            success: function (res) {
              console.log("wx.getUserInfo:");
              console.log(res.userInfo);
              //save to globaldata.
              setGlobalData("nickName",res.userInfo.nickName);
              setGlobalData("gender", res.userInfo.gender);
              setGlobalData("avatarUrl", res.userInfo.avatarUrl);
              setGlobalData("city", res.userInfo.city);
              setGlobalData("country", res.userInfo.country);
              setGlobalData("language", res.userInfo.language);
              setGlobalData("province", res.userInfo.province);
            }
          })
        }
      }
    })
    // openid sync.
    console.log("getWxCode...");
    var _this = this;
    // wx.navigateTo({url: '/pages/login/login'});
    wx.login({
      success: function (res) {
        console.log("wx.login success response:", res);
        var appId = 'wxb91ed27f01c09f0d';//appid iStorybook
        // var appSecret = '4caa1c4c4dXd9f05706816570e1e462cXX';//的app secret
        var js_code = res.code;//code
        console.log("js_code:", js_code);
        //only works for debugging
        // wx.request({
        //   url: 'https://api.weixin.qq.com/sns/jscode2session?appid=' + appId + '&secret=' + appSecret + '&js_code=' + js_code + '&grant_type=authorization_code',
        //   data: {},
        //   method: 'GET',
        //   success: function (res) {
        //     var openid = res.data.openid //返回的用户唯一标识符openid
        //     console.log("openid:",openid);
        //   }
        // });
        //http://localhost:8080/storybook/storybook/page/
        wx.request({
          url: API_WX_USER_LOGIN + appId + '/login?' + 'code=' + js_code,
          data: {},
          method: 'GET',
          success: function (res) {
            console.log("wx/user/appid/login resp:", res);
            var openid = res.data.openid //返回的用户唯一标识符openid
            console.log("openid:", openid);
            //
            setGlobalData("openid", openid);
            //
          }
        });
      }
    });
  }
   ,bindGetUserInfo: function (e) {
     console.log("bindGetUserInfo:");
     console.log(e.detail.userInfo);
    //sync user info.
    //http://localhost:8080/storybook/storybook/page/
     wx.request({
       url: API_WX_USER_SAVE,
       data: {
         "openid": getGlobalData("openid"),
         "nickName": getGlobalData("nickName"),
         "gender": getGlobalData("gender"),
         "avatarUrl": getGlobalData("avatarUrl"),
         "city": getGlobalData("city"),
         "country": getGlobalData("country"),
         "language": getGlobalData("language"),
         "province": getGlobalData("province")
       },
       method: 'POST',
       header: {
         'content-type': 'application/json'
       }, 
      //  header:{
      //   'content-type':'application/x-www-form-urlencoded'
      //  },
       success: function (res) {
         console.log(API_WX_USER_SAVE +" success resp:", res);
         //redirect to index page.
         wx.switchTab({
           url: '/pages/index/index'
         });
         //
       },
       fail:function(err) {
         console.error(API_WX_USER_SAVE+" fail:", err);
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