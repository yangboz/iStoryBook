CREATE TABLE `T_STORYBOOK_PAGE` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `mode` char(50) NOT NULL DEFAULT 'public' COMMENT 'public or private',
  `width` char(25) NOT NULL DEFAULT '100' COMMENT '',
  `height` tinyint(1) NOT NULL DEFAULT '100' COMMENT '',
  `views` varchar(500) NOT NULL DEFAULT '' COMMENT 'storybookpage views',
  `create_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建于',
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新于',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24577 DEFAULT CHARSET=utf8;