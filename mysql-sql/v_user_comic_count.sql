CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `tongrenlu`@`%` 
    SQL SECURITY DEFINER
VIEW `v_user_comic_count` AS
    select 
        count(`v_comic`.`id`) AS `cnt`,
        `v_comic`.`userBean.id` AS `userId`
    from
        `v_comic`
    group by `v_comic`.`userBean.id`