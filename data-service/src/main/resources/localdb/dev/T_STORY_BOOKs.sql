--
--DROP TABLE IF EXISTS `T_STORY_BOOK`;
--
--CREATE TABLE `T_STORYBOOK` (
--  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '',
--  `title` char(10) NOT NULL DEFAULT 'no title' COMMENT 'public or private',
--  `author` char(50) NOT NULL DEFAULT '' COMMENT '',
--  `description` varchar(144) NOT NULL DEFAULT '' COMMENT '',
--  `rate` int(10) NOT NULL DEFAULT '0' COMMENT '',
--  `share` int(10) NOT NULL DEFAULT '0' COMMENT '',
--  `width` int(10) NOT NULL DEFAULT '100' COMMENT '',
--  `height` int(10) NOT NULL DEFAULT '100' COMMENT '',
--  `mode` char(10) NOT NULL DEFAULT 'public' COMMENT 'public or private',
--  `pages` varchar(255) NOT NULL DEFAULT '0' COMMENT 'storybook pages',
--  `cover` varchar(255) NOT NULL DEFAULT '' COMMENT 'storybookpage views',
--  `create_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '',
--  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '',
--  PRIMARY KEY (`id`)
--) ENGINE=InnoDB AUTO_INCREMENT=24577 DEFAULT CHARSET=UTF8;

/*Data for the table `T_STORYBOOK_PAGE` */

insert  into `T_STORY_BOOK`(`id`,`author`,`title`,`description`,`cover`,`rate`,`share`,`width`,`height`,`create_at`,`update_at`) values
(1,'官方作品','骑士的勇气','美丽的公主被魔王抢走，勇敢的小骑士Mark开启了营救之旅...','http://knighter.xyz:82/assets/previews/low/1.jpg',0,0,100,100,'2018-12-28 23:01:35','2018-12-28 23:01:35'),
(2,'oyZCt4rMnjmn8lvFRTLbzi05CBfA','疯狂捉迷藏','玩捉迷藏啦～小松鼠Tomas有一个疯狂的不被人发现的想法...','http://knighter.xyz:82/assets/previews/low/2.jpg',0,0,200,200,'2018-12-28 23:01:35'
,'2018-12-28 23:01:35');