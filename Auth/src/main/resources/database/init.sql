
INSERT INTO `evan_sso`.`permission` (`id`, `name`) VALUES ('1', 'SUPER_ADMIN');

INSERT INTO `evan_sso`.`role` (`id`, `name`) VALUES ('1', '超级管理员');

INSERT INTO `evan_sso`.`roles_permissions` (`role_id`, `permission_id`) VALUES ('1', '1');

INSERT INTO `evan_sso`.`users_roles` (`user_name`, `role_id`) VALUES ('superadmin', '1');


