
DROP TABLE IF EXISTS `T_STORYPAGE`;

CREATE TABLE `T_STORYPAGE` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `mode` char(10) NOT NULL DEFAULT 'public' COMMENT 'public or private',
  `width` int(10) NOT NULL DEFAULT '100' COMMENT '',
  `height` int(10) NOT NULL DEFAULT '100' COMMENT '',
  `views` varchar(255) NOT NULL DEFAULT '' COMMENT 'storybookpage views',
  `create_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建于',
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新于',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24577 DEFAULT CHARSET=utf8;

/*Data for the table `T_STORYBOOK_PAGE` */

insert  into `T_STORYPAGE`(`id`,`mode`,`width`,`height`,`views`,`create_at`,`update_at`) values
(1,'public',100,100,'[{
	"id": 1,
	"top": 0,
	"left": 0,
	"width": 100,
	"height": 100,
	"type": "image",
	"url": "http://knighter.xyz:82/assets/templates/1.jpg"
}]','2018-12-28 23:01:35','2018-12-28 23:01:35'),
(2,'public',200,200,'[{
	"id": 2,
	"top": 0,
	"left": 0,
	"width": 100,
	"height": 100,
	"type": "image",
	"url": "http://knighter.xyz:82/assets/templates/2.jpg"
}]','2018-12-28 23:01:35','2018-12-28 23:01:35'),
(3,'public',300,300,'[{
	"id": 3,
	"top": 0,
	"left": 0,
	"width": 100,
	"height": 100,
	"type": "image",
	"url": "http://knighter.xyz:82/assets/templates/3.jpg"
}]','2018-12-28 23:01:35','2018-12-28 23:01:35'),
(4,'public',400,400,'[{
	"id": 4,
	"top": 0,
	"left": 0,
	"width": 100,
	"height": 100,
	"type": "image",
	"url": "http://knighter.xyz:82/assets/templates/4.jpg"
}]','2018-12-28 23:01:36','2018-12-28 23:01:36'),
(5,'private',500,500,'[{
	"id": 5,
	"top": 0,
	"left": 0,
	"width": 100,
	"height": 100,
	"type": "image",
	"url": "http://knighter.xyz:82/assets/templates/5.jpg"
}]','2018-12-28 23:01:37','2018-12-28 23:01:37'),
(6,'private',600,600,'[{
	"id": 6,
	"top": 0,
	"left": 0,
	"width": 100,
	"height": 100,
	"type": "image",
	"url": "http://knighter.xyz:82/assets/templates/6.jpg"
}]','2018-12-28 23:01:37','2018-12-28 23:01:37'),
(7,'private',700,700,'[{
	"id": 7,
	"top": 0,
	"left": 0,
	"width": 100,
	"height": 100,
	"type": "image",
	"url": "http://knighter.xyz:82/assets/templates/7.jpg"
}]','2018-12-28 23:01:37','2018-12-28 23:01:37');