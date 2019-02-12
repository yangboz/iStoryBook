
DROP TABLE IF EXISTS `T_STORYBOOK_PAGE`;

CREATE TABLE `T_STORYBOOK_PAGE` (
  `storybook_id` int(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '',
  `storypage_id` int(19) unsigned NOT NULL AUTO_INCREMENT COMMENT ''
) ENGINE=InnoDB AUTO_INCREMENT=24577 DEFAULT CHARSET=utf8;

/*Data for the table `T_STORYBOOK_PAGE` */

insert  into `T_STORYBOOK_PAGE`(`storybook_id`,`storypage_id`) values
(1,1),
(2,2);
