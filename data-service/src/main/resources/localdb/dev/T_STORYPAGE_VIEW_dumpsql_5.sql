
DROP TABLE IF EXISTS `T_STORYPAGE_VIEW`;

CREATE TABLE `T_STORYPAGE_VIEW` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `type` char(10) NOT NULL DEFAULT 'image' COMMENT 'image,text,rect',
  `width` int(10) NOT NULL DEFAULT '100' COMMENT '',
  `height` int(10) NOT NULL DEFAULT '100' COMMENT '',
  `top` int(10) NOT NULL DEFAULT '10' COMMENT '',
  `left` int(10) NOT NULL DEFAULT '10' COMMENT '',
  `url` varchar(255) NOT NULL DEFAULT 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJA7Siay35wOVVuGZ9mMicnLkof0kAvowk0wibuHibIfH42uqtODEdYoIx7AFmh6K8LD1E79LmrtvwAcw/132' COMMENT 'image url',
  `create_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建于',
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新于',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24577 DEFAULT CHARSET=utf8;

/*Data for the table `T_STORYBOOK_PAGE` */

insert  into `T_STORYPAGE_VIEW`(`id`,`type`,`url`,`width`,`height`,`top`,`left`,`create_at`,`update_at`) values
(1,'public','http://knighter.xyz:82/assets/templates/1.jpg',100,100,10,10,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(2,'public','http://knighter.xyz:82/assets/templates/2.jpg',200,200,10,10,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(3,'public','http://knighter.xyz:82/assets/templates/3.jpg',300,300,10,10,'2019-12-28 23:01:35','2018-12-28 23:01:35'),
(4,'public','http://knighter.xyz:82/assets/templates/4.jpg',400,400,10,10,'2019-12-28 23:01:36','2018-12-28 23:01:36'),
(5,'private','http://knighter.xyz:82/assets/templates/5.jpg',500,500,10,10,'2019-12-28 23:01:37','2018-12-28 23:01:37'),
(6,'private','http://knighter.xyz:82/assets/templates/6.jpg',600,600,10,10,'2019-12-28 23:01:37','2018-12-28 23:01:37'),
(7,'private','http://knighter.xyz:82/assets/templates/7.jpg',700,700,10,10,'2019-12-28 23:01:37','2018-12-28 23:01:37');