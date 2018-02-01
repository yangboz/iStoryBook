# DeepDetectMessager
SNS+iMessager for DeepDetect(Earth most centric chatbots app) demostration.

![Screenshot of master view](https://github.com/yangboz/DeepDetectMessager/blob/master/ChatBotJSQMessager/screenshots/Simulator%20Screen%20Shot%2010%20Apr%202017%2C%2011.30.25.png)
![Screenshot of master view](https://github.com/yangboz/DeepDetectMessager/blob/master/ChatBotJSQMessager/screenshots/master.jpeg)
![Screenshot of detail view](https://github.com/yangboz/DeepDetectMessager/blob/master/ChatBotJSQMessager/screenshots/detail.jpeg)

#DeepDetect Server（4G RAM+）

1.caffe:https://github.com/BVLC/caffe/wiki/Ubuntu-16.04-or-15.10-Installation-Guide

2.curlpp:https://github.com/beniz/deepdetect/issues/126

3.deepdetect:https://deepdetect.com/overview/installing/

##ImageNet Classification Service

1.pull and run docker

`
docker run -d -p 8080:8080 beniz/deepdetect_cpu
`

1.1 build and run

`
git clone https://github.com/beniz/deepdetect.git
`

`
nohup ./main/dede -host 118.190.3.169 -port 8090 > dede.out 2>&1&
`

2.create ImageNet/ggnet service

`
curl -X PUT "http://118.190.96.120:8090/services/imageserv" -d '{"mllib":"caffe", "description":"image classification service", "type":"supervised", "parameters":{"input":{"connector":"image", "width":224, "height":224 }, "mllib":{"template":"googlenet", "nclasses":1000 } }, "model":{"templates":"../templates/caffe/", "repository":"/root/models/imgnet"} }'
`

3.test service

`
curl -X POST "http://118.190.96.120:8090/predict" -d "{\"service\":\"imageserv\",\"parameters\":{\"input\":{\"width\":224,\"height\":224},\"output\":{\"best\":3},\"mllib\":{\"gpu\":false}},\"data\":[\"https://deepdetect.com/img/ambulance.jpg\"]}"
`

4.kill docker

`
docker rm -fv 1ca885426d1a
`

5.delete a service

`
curl -X DELETE "http://localhost:8080/services/imageserv?clear=full"
`

##Bags Classification Service


1.create bags service

`
curl -X PUT "http://118.190.96.120:8090/services/bags" -d '{"mllib":"caffe", "description":"bags classification", "type":"supervised", "parameters":{"input":{"connector":"image", "height":224, "width":224 }, "mllib":{"nclasses":37 } }, "model":{"repository":"/root/models/bags"} }'
`

2.test service

`
curl -X POST "http://118.190.96.120:8090/predict" -d "{\"service\":\"bags\",\"parameters\":{\"input\":{\"width\":224,\"height\":224},\"output\":{\"best\":3},\"mllib\":{\"gpu\":false}},\"data\":[\"http://118.190.3.169/images/nike-golf-bag.jpg\"]}"
`

##Footwear Classification Service


1.create footwear service

`
curl -X PUT "http://118.190.96.120:8090/services/footwear" -d '{"mllib":"caffe", "description":"footwear classification", "type":"supervised", "parameters":{"input":{"connector":"image", "height":224, "width":224 }, "mllib":{"nclasses":51 } }, "model":{"repository":"/root/models/footwear"} }'
`

2.test service

`
curl -X POST "http://118.190.96.120:8090/predict" -d "{\"service\":\"footwear\",\"parameters\":{\"input\":{\"width\":224,\"height\":224},\"output\":{\"best\":3},\"mllib\":{\"gpu\":false}},\"data\":[\"http://118.190.3.169/images/men-footwear.jpg\"]}"
`

##Clothing Classification Service


1.create clothing service (Error while proceeding with prediction forward pass, not enough memory?)

`
curl -X PUT "http://118.190.96.120:8090/services/clothing" -d '{"mllib":"caffe", "description":"clothes classification", "type":"supervised", "parameters":{"input":{"connector":"image", "height":224, "width":224 }, "mllib":{"nclasses":304 } }, "model":{"repository":"/root/models/clothing"} }'
`

2.test service

`
curl -X POST "http://118.190.96.120:8090/predict" -d "{\"service\":\"clothing\",\"parameters\":{\"input\":{\"width\":224,\"height\":224},\"output\":{\"best\":3},\"mllib\":{\"gpu\":false}},\"data\":[\"http://118.190.3.169/images/faux-fur-coat-winter-2014-big-trend-10.jpg\"]}"
`

##Buildings Classification Service


1.create buildings service(Error while proceeding with prediction forward pass, not enough memory?)

`
curl -X PUT "http://118.190.96.120:8090/services/buildings" -d '{"mllib":"caffe", "description":"buildings classification", "type":"supervised", "parameters":{"input":{"connector":"image", "height":224, "width":224 }, "mllib":{"nclasses":185 } }, "model":{"repository":"/root/models/buildings"} }'
`

2.test service

`
curl -X POST "http://118.190.96.120:8090/predict" -d "{\"service\":\"buildings\",\"parameters\":{\"input\":{\"width\":224,\"height\":224},\"output\":{\"best\":3},\"mllib\":{\"gpu\":false}},\"data\":[\"http://118.190.3.169/images/Temple-of-Heaven.jpg\"]}"
`

##Fabric Classification Service


1.create fabric service

`
curl -X PUT "http://118.190.96.120:8090/services/fabric" -d '{"mllib":"caffe", "description":"fabric classification", "type":"supervised", "parameters":{"input":{"connector":"image", "height":224, "width":224 }, "mllib":{"nclasses":233 } }, "model":{"repository":"/root/models/fabric"} }'
`

2.test service

`
curl -X POST "http://118.190.96.120:8090/predict" -d "{\"service\":\"fabric\",\"parameters\":{\"input\":{\"width\":224,\"height\":224},\"output\":{\"best\":3},\"mllib\":{\"gpu\":false}},\"data\":[\"http://118.190.3.169/images/tropical-beach-house.jpg\"]}"
`

##Age Classification Service


1.create age service

`
curl -X PUT "http://118.190.96.120:8090/services/age" -d '{"mllib":"caffe", "description":"age classification", "type":"supervised", "parameters":{"input":{"connector":"image", "height":224, "width":224 }, "mllib":{"nclasses":8 } }, "model":{"repository":"/root/models/age_model"} }'
`

2.test service


`
curl -X POST "http://118.190.96.120:8090/predict" -d "{\"service\":\"age\",\"parameters\":{\"input\":{\"width\":224,\"height\":224},\"output\":{\"best\":2},\"mllib\":{\"gpu\":false}},\"data\":[\"http://118.190.3.169/images/President_Barack_Obama.jpg\"]}"
`

##Gender Classification Service


1.create gender service

`
curl -X PUT "http://118.190.96.120:8090/services/gender" -d '{"mllib":"caffe", "description":"gender classification", "type":"supervised", "parameters":{"input":{"connector":"image", "height":224, "width":224 }, "mllib":{"nclasses":2 } }, "model":{"repository":"/root/models/gender"} }'
`

2.test service

`
curl -X POST "http://118.190.96.120:8090/predict" -d "{\"service\":\"gender\",\"parameters\":{\"input\":{\"width\":224,\"height\":224},\"output\":{\"best\":2},\"mllib\":{\"gpu\":false}},\"data\":[\"http://118.190.3.169/images/President_Barack_Obama.jpg\"]}"
`

##Sports Classification Service


1.create sports service

`
curl -X PUT "http://118.190.96.120:8090/services/sports" -d '{"mllib":"caffe", "description":"sports classification", "type":"supervised", "parameters":{"input":{"connector":"image", "height":224, "width":224 }, "mllib":{"nclasses":143 } }, "model":{"repository":"/root/models/sports"} }'
`

2.test service

`
curl -X POST "http://118.190.96.120:8090/predict" -d "{\"service\":\"sports\",\"parameters\":{\"input\":{\"width\":224,\"height\":224},\"output\":{\"best\":3},\"mllib\":{\"gpu\":false}},\"data\":[\"http://118.190.3.169/images/Jordan_by_Lipofsky_16577.jpg\"]}"
`

##Trees Classification Service


1.create trees service

`
curl -X PUT "http://118.190.96.120:8090/services/trees" -d '{"mllib":"caffe", "description":"trees classification", "type":"supervised", "parameters":{"input":{"connector":"image", "height":224, "width":224 }, "mllib":{"nclasses":890 } }, "model":{"repository":"/root/models/trees"} }'
`

2.test service

`
curl -X POST "http://118.190.96.120:8090/predict" -d "{\"service\":\"trees\",\"parameters\":{\"input\":{\"width\":224,\"height\":224},\"output\":{\"best\":3},\"mllib\":{\"gpu\":false}},\"data\":[\"http://118.190.3.169/images/cherry-plum-tree.jpg\"]}"
`

##Sentiment analysis Service


1.create sentiment service

`
curl -X PUT 'http://118.190.96.120:8090/services/sent_en' -d '{"mllib":"caffe", "description":"English sentiment classification", "type":"supervised", "parameters":{"input":{"connector":"txt", "characters":true, "alphabet":"abcdefghijklmnopqrstuvwxyz0123456789,;.!?'\''", "sequence":140 }, "mllib":{"nclasses":2 } }, "model":{"repository":"/root/models/sent_en_char"} }'
`

2.test service

`
curl -X POST 'http://118.190.96.120:8090/predict' -d '{"service":"sent_en", "parameters":{"mllib":{"gpu":true } }, "data":["Chilling in the West Indies"] }'
`

##Furnitrues classification Service(179)


1.create furnitrues service

`
curl -X PUT "http://118.190.96.120:8090/services/furnitrues" -d '{"mllib":"caffe", "description":"Furnitrues classification", "type":"supervised", "parameters":{"input":{"connector":"image", "height":224, "width":224 }, "mllib":{"nclasses":143 } }, "model":{"repository":"/root/models/furnitures"} }'
`

2.test service

`
curl -X POST "http://118.190.96.120:8090/predict" -d "{\"service\":\"trees\",\"parameters\":{\"input\":{\"width\":224,\"height\":224},\"output\":{\"best\":3},\"mllib\":{\"gpu\":false}},\"data\":[\"http://118.190.3.169/images/furnitures-friends.jpg\"]}"
`
##Object detection Service(21)


1.create object detection service

`
curl -X PUT "http://118.190.96.120:8090/services/objectdetect" -d '{"mllib":"caffe", "description":"object detection service", "type":"supervised", "parameters":{"input":{"connector":"image", "height": 300, "width": 300 }, "mllib":{"nclasses":21 } }, "model":{"repository":"/root/models/voc0712/"} }'
`

2.test service

`
curl -X POST "http://118.190.96.120:8090/predict" -d '{"service":"objectdetect", "parameters":{"output":{"bbox": true, "confidence_threshold": 0.1 } }, "data":["http://118.190.3.169/images/France_object_detect.jpeg"] }'
`

##Face detection Service(5)

##Prisma art style Service(12)

##Nsynth music generator Service(12)

##Fairseq translation Service(4)

##Chessboard FEN detection Service(7)

##Realtime object recognition using Tensorflow

https://medium.com/towards-data-science/building-a-real-time-object-recognition-app-with-tensorflow-and-opencv-b7a2b4ebdc32

##Banking chatbots with Watson

https://developer.ibm.com/code/journey/create-cognitive-banking-chatbot/

##Cats faces generator

https://github.com/AlexiaJM/Deep-learning-with-cats

## COVIAS Server



## Elastic Image Search

`
nohup /usr/share/elasticsearch/bin/elasticsearch -Des.insecure.allow.root=true > es.out 2>&1&
`

# References

DeepDetect(LGPL): https://deepdetect.com/

DeepDetect Models: https://deepdetect.com/applications/model/

iOS ChatbotMessager: https://github.com/yangboz/ChatBotsMessager

## Support on Beerpay
Hey dude! Help me out for a couple of :beers:!

[![Beerpay](https://beerpay.io/yangboz/DeepDetectMessager/badge.svg?style=beer-square)](https://beerpay.io/yangboz/DeepDetectMessager)  [![Beerpay](https://beerpay.io/yangboz/DeepDetectMessager/make-wish.svg?style=flat-square)](https://beerpay.io/yangboz/DeepDetectMessager?focus=wish)
