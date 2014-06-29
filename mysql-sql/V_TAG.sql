CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `v_tag` AS
    select 
        `m_tag`.`id` AS `id`,
        `m_tag`.`tag` AS `tag`,
        count(`r_article_tag`.`id`) AS `cnt`
    from
        ((`r_article_tag`
        left join `m_tag` ON ((`m_tag`.`id` = `r_article_tag`.`tag_id`)))
        left join `m_article` ON ((`m_article`.`id` = `r_article_tag`.`article_id`)))
    where
        ((`m_article`.`del_flg` = 0)
            and (`m_tag`.`del_flg` = 0)
            and (`r_article_tag`.`del_flg` = 0))
    group by `m_tag`.`id` , `m_tag`.`tag`;
