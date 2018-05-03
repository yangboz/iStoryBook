<?xml version="1.0" encoding="UTF-8"?>
<project name="KnightBrave" version="2.2" showFps="0" openLog="1" fps="60" scaleMode="0" orientaion="0" renderMode="1" bgColor="0" stagewidth="2600" stageheight="1299" startscenename="MainScene">
  <objects>
    <type uiname="Touch" uitype="AITouch"/>
    <type uiname="System" uitype="AISystem"/>
    <type uiname="Keyboard" uitype="AIKeyboard"/>
    <type uiname="Global" uitype="Global"/>
    <type uiname="Function" uitype="AIFunction"/>
    <type uiname="WebStorage" uitype="AIWebStorage"/>
    <type uiname="WebSocket" uitype="AIWebSocket"/>
    <type uiname="Browser" uitype="AIBrowser"/>
    <type uiname="Ajax" uitype="AIAjax"/>
    <type uiname="MainScene" uitype="Scene"/>
    <type uiname="backgroud" uitype="Layer"/>
    <type uiname="StoryScene" uitype="Scene"/>
    <type uiname="Layer4" uitype="Layer"/>
    <type uiname="AISprite7" uitype="AISprite"/>
    <type uiname="AISprite10" uitype="AISprite"/>
    <type uiname="AISprite13" uitype="AISprite"/>
    <type uiname="AISprite16" uitype="AISprite"/>
    <type uiname="AISprite19" uitype="AISprite"/>
    <type uiname="AISprite22" uitype="AISprite"/>
  </objects>
  <eventsheet>
    <sheet name="MainSceneEventSheet" targetscene="MainScene"/>
    <sheet name="StorySceneEventSheet" targetscene="StoryScene"/>
  </eventsheet>
  <scenes>
    <object type="Scene" uiname="MainScene" layer="0">
      <familys/>
      <properties>
        <p key="sceneWidth" value="2600" valuetype="number"/>
        <p key="sceneHeight" value="1299" valuetype="number"/>
        <p key="width" value="1708" valuetype="number"/>
        <p key="height" value="960" valuetype="number"/>
      </properties>
      <customproperties/>
      <behaviors/>
      <children>
        <object type="Layer" uiname="backgroud" parallaxX="100" parallaxY="100" layer="0">
          <properties>
            <p key="sceneWidth" value="2600" valuetype="number"/>
            <p key="sceneHeight" value="1299" valuetype="number"/>
            <p key="parallaxX" value="100" valuetype="number"/>
            <p key="parallaxY" value="100" valuetype="number"/>
            <p key="layerAlpha" value="1" valuetype="number"/>
            <p key="layerVisible" value="true" valuetype="boolean"/>
            <p key="layerScaleX" value="100" valuetype="number"/>
            <p key="layerScaleY" value="100" valuetype="number"/>
            <p key="cacheAsBitmap" value="false" valuetype="boolean"/>
          </properties>
          <customproperties/>
          <behaviors/>
          <children>
            <object type="AISprite" uiname="AISprite22" layer="0">
              <properties>
                <p key="name" value="AISprite22" valuetype="string">
                  <description>Name%20of%20the%20object.</description>
                </p>
                <p key="uiGuid" value="23" valuetype="string">
                  <description>GUID</description>
                </p>
                <p key="global" value="false" valuetype="boolean">
                  <description>Set%20the%20object%20global%20or%20not.%20The%20global%20object%20will%20be%20shown%20on%20all%20scenes.%20It%20will%20not%20be%20destroyed%20after%20jump%20to%20another%20scene%20and%20the%20data%20of%20the%20object%20will%20be%20keeped.</description>
                </p>
                <p key="visible" value="true" valuetype="boolean">
                  <description>Set%20the%20object%20visible%20or%20invisible.</description>
                </p>
                <p key="x" value="1802.75" valuetype="number">
                  <description>X%20co-ordinate.</description>
                </p>
                <p key="y" value="1479.8" valuetype="number">
                  <description>Y%20co-ordinate.</description>
                </p>
                <p key="anchorX" value="0.5" valuetype="number">
                  <description>The%20X%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="anchorY" value="0.5" valuetype="number">
                  <description>The%20Y%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="width" value="30" valuetype="number">
                  <description>The%20object's%20width.</description>
                </p>
                <p key="height" value="30" valuetype="number">
                  <description>The%20object's%20height.</description>
                </p>
                <p key="angle" value="0" valuetype="number">
                  <description>The%20object's%20angle.</description>
                </p>
                <p key="alpha" value="1" valuetype="number">
                  <description>The%20object's%20opacity.</description>
                </p>
                <p key="url" value="%22%22" valuetype="string">
                  <description>The%20source%20of%20the%20image.</description>
                </p>
                <p key="enabled" value="false" valuetype="boolean">
                  <description>Set%20clickable%20status%20of%20the%20object.</description>
                </p>
                <p key="collision" value="false" valuetype="boolean">
                  <description>Set%20collision%20status%20of%20the%20object.</description>
                </p>
                <p key="collisionData" value="%22%22" valuetype="string">
                  <description>Edit%20the%20collision%20edge.</description>
                </p>
              </properties>
              <customproperties/>
              <behaviors/>
              <children/>
            </object>
            <object type="AISprite" uiname="AISprite19" layer="0">
              <properties>
                <p key="name" value="AISprite19" valuetype="string">
                  <description>Name%20of%20the%20object.</description>
                </p>
                <p key="uiGuid" value="20" valuetype="string">
                  <description>GUID</description>
                </p>
                <p key="global" value="false" valuetype="boolean">
                  <description>Set%20the%20object%20global%20or%20not.%20The%20global%20object%20will%20be%20shown%20on%20all%20scenes.%20It%20will%20not%20be%20destroyed%20after%20jump%20to%20another%20scene%20and%20the%20data%20of%20the%20object%20will%20be%20keeped.</description>
                </p>
                <p key="visible" value="true" valuetype="boolean">
                  <description>Set%20the%20object%20visible%20or%20invisible.</description>
                </p>
                <p key="x" value="1372.6152376431287" valuetype="number">
                  <description>X%20co-ordinate.</description>
                </p>
                <p key="y" value="873.6829867658887" valuetype="number">
                  <description>Y%20co-ordinate.</description>
                </p>
                <p key="anchorX" value="0.5" valuetype="number">
                  <description>The%20X%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="anchorY" value="0.5" valuetype="number">
                  <description>The%20Y%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="width" value="127" valuetype="number">
                  <description>The%20object's%20width.</description>
                </p>
                <p key="height" value="849" valuetype="number">
                  <description>The%20object's%20height.</description>
                </p>
                <p key="angle" value="0" valuetype="number">
                  <description>The%20object's%20angle.</description>
                </p>
                <p key="alpha" value="1" valuetype="number">
                  <description>The%20object's%20opacity.</description>
                </p>
                <p key="url" value="resource%2FuserAsset%2FMenu_Branches.png" valuetype="string">
                  <description>The%20source%20of%20the%20image.</description>
                </p>
                <p key="enabled" value="false" valuetype="boolean">
                  <description>Set%20clickable%20status%20of%20the%20object.</description>
                </p>
                <p key="collision" value="false" valuetype="boolean">
                  <description>Set%20collision%20status%20of%20the%20object.</description>
                </p>
                <p key="collisionData" value="%22%22" valuetype="string">
                  <description>Edit%20the%20collision%20edge.</description>
                </p>
              </properties>
              <customproperties/>
              <behaviors/>
              <children/>
            </object>
            <object type="AISprite" uiname="AISprite16" layer="0">
              <properties>
                <p key="name" value="AISprite16" valuetype="string">
                  <description>Name%20of%20the%20object.</description>
                </p>
                <p key="uiGuid" value="17" valuetype="string">
                  <description>GUID</description>
                </p>
                <p key="global" value="false" valuetype="boolean">
                  <description>Set%20the%20object%20global%20or%20not.%20The%20global%20object%20will%20be%20shown%20on%20all%20scenes.%20It%20will%20not%20be%20destroyed%20after%20jump%20to%20another%20scene%20and%20the%20data%20of%20the%20object%20will%20be%20keeped.</description>
                </p>
                <p key="visible" value="true" valuetype="boolean">
                  <description>Set%20the%20object%20visible%20or%20invisible.</description>
                </p>
                <p key="x" value="1302.699081819343" valuetype="number">
                  <description>X%20co-ordinate.</description>
                </p>
                <p key="y" value="1146.6565622464936" valuetype="number">
                  <description>Y%20co-ordinate.</description>
                </p>
                <p key="anchorX" value="0.5" valuetype="number">
                  <description>The%20X%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="anchorY" value="0.5" valuetype="number">
                  <description>The%20Y%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="width" value="2600" valuetype="number">
                  <description>The%20object's%20width.</description>
                </p>
                <p key="height" value="304" valuetype="number">
                  <description>The%20object's%20height.</description>
                </p>
                <p key="angle" value="0" valuetype="number">
                  <description>The%20object's%20angle.</description>
                </p>
                <p key="alpha" value="1" valuetype="number">
                  <description>The%20object's%20opacity.</description>
                </p>
                <p key="url" value="resource%2FuserAsset%2FMenu_UL1.png" valuetype="string">
                  <description>The%20source%20of%20the%20image.</description>
                </p>
                <p key="enabled" value="false" valuetype="boolean">
                  <description>Set%20clickable%20status%20of%20the%20object.</description>
                </p>
                <p key="collision" value="false" valuetype="boolean">
                  <description>Set%20collision%20status%20of%20the%20object.</description>
                </p>
                <p key="collisionData" value="%22%22" valuetype="string">
                  <description>Edit%20the%20collision%20edge.</description>
                </p>
              </properties>
              <customproperties/>
              <behaviors/>
              <children/>
            </object>
            <object type="AISprite" uiname="AISprite13" layer="0">
              <properties>
                <p key="name" value="AISprite13" valuetype="string">
                  <description>Name%20of%20the%20object.</description>
                </p>
                <p key="uiGuid" value="14" valuetype="string">
                  <description>GUID</description>
                </p>
                <p key="global" value="false" valuetype="boolean">
                  <description>Set%20the%20object%20global%20or%20not.%20The%20global%20object%20will%20be%20shown%20on%20all%20scenes.%20It%20will%20not%20be%20destroyed%20after%20jump%20to%20another%20scene%20and%20the%20data%20of%20the%20object%20will%20be%20keeped.</description>
                </p>
                <p key="visible" value="true" valuetype="boolean">
                  <description>Set%20the%20object%20visible%20or%20invisible.</description>
                </p>
                <p key="x" value="1300.0935315296692" valuetype="number">
                  <description>X%20co-ordinate.</description>
                </p>
                <p key="y" value="1123.6940020592997" valuetype="number">
                  <description>Y%20co-ordinate.</description>
                </p>
                <p key="anchorX" value="0.5" valuetype="number">
                  <description>The%20X%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="anchorY" value="0.5" valuetype="number">
                  <description>The%20Y%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="width" value="2600" valuetype="number">
                  <description>The%20object's%20width.</description>
                </p>
                <p key="height" value="341" valuetype="number">
                  <description>The%20object's%20height.</description>
                </p>
                <p key="angle" value="0" valuetype="number">
                  <description>The%20object's%20angle.</description>
                </p>
                <p key="alpha" value="1" valuetype="number">
                  <description>The%20object's%20opacity.</description>
                </p>
                <p key="url" value="resource%2FuserAsset%2FMenu_UL2.png" valuetype="string">
                  <description>The%20source%20of%20the%20image.</description>
                </p>
                <p key="enabled" value="false" valuetype="boolean">
                  <description>Set%20clickable%20status%20of%20the%20object.</description>
                </p>
                <p key="collision" value="false" valuetype="boolean">
                  <description>Set%20collision%20status%20of%20the%20object.</description>
                </p>
                <p key="collisionData" value="%22%22" valuetype="string">
                  <description>Edit%20the%20collision%20edge.</description>
                </p>
              </properties>
              <customproperties/>
              <behaviors/>
              <children/>
            </object>
            <object type="AISprite" uiname="AISprite10" layer="0">
              <properties>
                <p key="name" value="AISprite10" valuetype="string">
                  <description>Name%20of%20the%20object.</description>
                </p>
                <p key="uiGuid" value="11" valuetype="string">
                  <description>GUID</description>
                </p>
                <p key="global" value="false" valuetype="boolean">
                  <description>Set%20the%20object%20global%20or%20not.%20The%20global%20object%20will%20be%20shown%20on%20all%20scenes.%20It%20will%20not%20be%20destroyed%20after%20jump%20to%20another%20scene%20and%20the%20data%20of%20the%20object%20will%20be%20keeped.</description>
                </p>
                <p key="visible" value="true" valuetype="boolean">
                  <description>Set%20the%20object%20visible%20or%20invisible.</description>
                </p>
                <p key="x" value="0" valuetype="number">
                  <description>X%20co-ordinate.</description>
                </p>
                <p key="y" value="0" valuetype="number">
                  <description>Y%20co-ordinate.</description>
                </p>
                <p key="anchorX" value="0" valuetype="number">
                  <description>The%20X%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="anchorY" value="0" valuetype="number">
                  <description>The%20Y%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="width" value="2600" valuetype="number">
                  <description>The%20object's%20width.</description>
                </p>
                <p key="height" value="1299" valuetype="number">
                  <description>The%20object's%20height.</description>
                </p>
                <p key="angle" value="0" valuetype="number">
                  <description>The%20object's%20angle.</description>
                </p>
                <p key="alpha" value="1" valuetype="number">
                  <description>The%20object's%20opacity.</description>
                </p>
                <p key="url" value="resource%2FuserAsset%2FMenu_UL.png" valuetype="string">
                  <description>The%20source%20of%20the%20image.</description>
                </p>
                <p key="enabled" value="false" valuetype="boolean">
                  <description>Set%20clickable%20status%20of%20the%20object.</description>
                </p>
                <p key="collision" value="false" valuetype="boolean">
                  <description>Set%20collision%20status%20of%20the%20object.</description>
                </p>
                <p key="collisionData" value="%22%22" valuetype="string">
                  <description>Edit%20the%20collision%20edge.</description>
                </p>
              </properties>
              <customproperties/>
              <behaviors/>
              <children/>
            </object>
            <object type="AISprite" uiname="AISprite7" layer="0">
              <properties>
                <p key="name" value="AISprite7" valuetype="string">
                  <description>Name%20of%20the%20object.</description>
                </p>
                <p key="uiGuid" value="8" valuetype="string">
                  <description>GUID</description>
                </p>
                <p key="global" value="false" valuetype="boolean">
                  <description>Set%20the%20object%20global%20or%20not.%20The%20global%20object%20will%20be%20shown%20on%20all%20scenes.%20It%20will%20not%20be%20destroyed%20after%20jump%20to%20another%20scene%20and%20the%20data%20of%20the%20object%20will%20be%20keeped.</description>
                </p>
                <p key="visible" value="true" valuetype="boolean">
                  <description>Set%20the%20object%20visible%20or%20invisible.</description>
                </p>
                <p key="x" value="0" valuetype="number">
                  <description>X%20co-ordinate.</description>
                </p>
                <p key="y" value="0" valuetype="number">
                  <description>Y%20co-ordinate.</description>
                </p>
                <p key="anchorX" value="0" valuetype="number">
                  <description>The%20X%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="anchorY" value="0" valuetype="number">
                  <description>The%20Y%20co-ordinate%20of%20anchor.</description>
                </p>
                <p key="width" value="2600" valuetype="number">
                  <description>The%20object's%20width.</description>
                </p>
                <p key="height" value="1299" valuetype="number">
                  <description>The%20object's%20height.</description>
                </p>
                <p key="angle" value="0" valuetype="number">
                  <description>The%20object's%20angle.</description>
                </p>
                <p key="alpha" value="1" valuetype="number">
                  <description>The%20object's%20opacity.</description>
                </p>
                <p key="url" value="resource%2FuserAsset%2FMenu_BG.png" valuetype="string">
                  <description>The%20source%20of%20the%20image.</description>
                </p>
                <p key="enabled" value="false" valuetype="boolean">
                  <description>Set%20clickable%20status%20of%20the%20object.</description>
                </p>
                <p key="collision" value="false" valuetype="boolean">
                  <description>Set%20collision%20status%20of%20the%20object.</description>
                </p>
                <p key="collisionData" value="%22%22" valuetype="string">
                  <description>Edit%20the%20collision%20edge.</description>
                </p>
              </properties>
              <customproperties/>
              <behaviors/>
              <children/>
            </object>
          </children>
        </object>
      </children>
    </object>
    <object type="Scene" uiname="StoryScene" layer="0">
      <familys/>
      <properties>
        <p key="sceneWidth" value="2600" valuetype="number"/>
        <p key="sceneHeight" value="1299" valuetype="number"/>
        <p key="width" value="1708" valuetype="number"/>
        <p key="height" value="960" valuetype="number"/>
      </properties>
      <customproperties/>
      <behaviors/>
      <children>
        <object type="Layer" uiname="Layer4" parallaxX="100" parallaxY="100" layer="0">
          <properties>
            <p key="sceneWidth" value="2600" valuetype="number"/>
            <p key="sceneHeight" value="1299" valuetype="number"/>
            <p key="parallaxX" value="100" valuetype="number"/>
            <p key="parallaxY" value="100" valuetype="number"/>
            <p key="layerAlpha" value="1" valuetype="number"/>
            <p key="layerVisible" value="true" valuetype="boolean"/>
            <p key="layerScaleX" value="100" valuetype="number"/>
            <p key="layerScaleY" value="100" valuetype="number"/>
            <p key="cacheAsBitmap" value="false" valuetype="boolean"/>
          </properties>
          <customproperties/>
          <behaviors/>
          <children/>
        </object>
      </children>
    </object>
  </scenes>
</project>