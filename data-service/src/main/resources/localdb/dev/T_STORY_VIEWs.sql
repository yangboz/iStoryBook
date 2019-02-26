--
--DROP TABLE IF EXISTS `T_STORYPAGE_VIEW`;
--
--CREATE TABLE `T_STORYPAGE_VIEW` (
--  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
--  `type` char(10) NOT NULL DEFAULT 'image' COMMENT 'image,text,rect',
--  `width` int(10) NOT NULL DEFAULT '100' COMMENT '',
--  `height` int(10) NOT NULL DEFAULT '100' COMMENT '',
--  `top` int(10) NOT NULL DEFAULT '10' COMMENT '',
--  `left` int(10) NOT NULL DEFAULT '10' COMMENT '',
--  `url` varchar(255) NOT NULL DEFAULT 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJA7Siay35wOVVuGZ9mMicnLkof0kAvowk0wibuHibIfH42uqtODEdYoIx7AFmh6K8LD1E79LmrtvwAcw/132' COMMENT 'image url',
--  `create_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建于',
--  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新于',
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB AUTO_INCREMENT=24577 DEFAULT CHARSET=utf8;

/*Data for the table `T_STORY_VIEW` */

insert  into `T_STORY_VIEW`(`id`,`url`,`width`,`height`,`top`,`left`,`create_at`,`update_at`) values
(1,'http://knighter.xyz:82/assets/previews/low/1_1.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(2,'http://knighter.xyz:82/assets/previews/low/1_2.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(3,'http://knighter.xyz:82/assets/previews/low/1_3.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(4,'http://knighter.xyz:82/assets/previews/low/1_4.jpg',2600,1299,0,0,'2019-12-28 23:01:36','2018-12-28 23:01:36'),
(5,'http://knighter.xyz:82/assets/previews/low/1_5.jpg',2600,1299,0,0,'2019-12-28 23:01:37','2018-12-28 23:01:37'),
(6,'http://knighter.xyz:82/assets/previews/low/1_6.jpg',2600,1299,0,0,'2019-12-28 23:01:37','2018-12-28 23:01:37'),
(7,'http://knighter.xyz:82/assets/previews/low/1_7.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(8,'http://knighter.xyz:82/assets/previews/low/1_8.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(9,'http://knighter.xyz:82/assets/previews/low/1_9.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(10,'http://knighter.xyz:82/assets/previews/low/1_10.jpg',2600,1299,0,0,'2019-12-28 23:01:36','2018-12-28 23:01:36'),
(11,'http://knighter.xyz:82/assets/previews/low/1_11.jpg',2600,1299,0,0,'2019-12-28 23:01:37','2018-12-28 23:01:37'),
(12,'http://knighter.xyz:82/assets/previews/low/1_12.jpg',2600,1299,0,0,'2019-12-28 23:01:37','2018-12-28 23:01:37'),
(13,'http://knighter.xyz:82/assets/previews/low/1_13.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(14,'http://knighter.xyz:82/assets/previews/low/1_14.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(15,'http://knighter.xyz:82/assets/previews/low/1_15.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(16,'http://knighter.xyz:82/assets/previews/low/1_16.jpg',2600,1299,0,0,'2019-12-28 23:01:36','2018-12-28 23:01:36'),
(17,'http://knighter.xyz:82/assets/previews/low/1_17.jpg',2600,1299,0,0,'2019-12-28 23:01:37','2018-12-28 23:01:37'),
(18,'http://knighter.xyz:82/assets/previews/low/1_18.jpg',2600,1299,0,0,'2019-12-28 23:01:37','2018-12-28 23:01:37'),
(19,'http://knighter.xyz:82/assets/previews/low/1_19.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(20,'http://knighter.xyz:82/assets/previews/low/1_20.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(21,'http://knighter.xyz:82/assets/previews/low/1_21.jpg',2600,1299,0,0,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(22,'http://knighter.xyz:82/assets/previews/low/1_22.jpg',2600,1299,0,0,'2019-12-28 23:01:36','2018-12-28 23:01:36'),
(23,'http://knighter.xyz:82/assets/previews/low/1_23.jpg',2600,1299,0,0,'2019-12-28 23:01:37','2018-12-28 23:01:37'),
(24,'http://knighter.xyz:82/assets/previews/low/1_24.jpg',2600,1299,0,0,'2019-12-28 23:01:37','2018-12-28 23:01:37');