CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `v_user_comic_count` AS
    select 
        `m_user`.`id` AS `userBean.id`,
        count(`v_comic`.`id`) AS `cnt`
    from
        (`v_comic`
        left join `m_user` ON ((`v_comic`.`userBean.id` = `m_user`.`id`)))
    group by `m_user`.`id`;
