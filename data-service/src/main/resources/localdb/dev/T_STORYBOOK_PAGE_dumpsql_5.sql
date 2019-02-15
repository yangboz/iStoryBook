
DROP TABLE IF EXISTS `T_STORYBOOK_PAGE`;

CREATE TABLE `T_STORYBOOK_PAGE` (
`id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '',
  `story_book` int(19) unsigned NOT NULL AUTO_INCREMENT COMMENT '',
  `story_page` int(19) unsigned NOT NULL AUTO_INCREMENT COMMENT ''
) ENGINE=InnoDB AUTO_INCREMENT=24577 DEFAULT CHARSET=utf8;

/*Data for the table `T_STORYBOOK_PAGE` */

insert  into `T_STORYBOOK_PAGE`(`id`,`story_book`,`story_page`) values
(1,1,1),
(2,1,2),
(3,2,4),
(4,2,5),
(5,1,3),
(6,2,6),
(7,2,7);