CREATE 
VIEW `v_tag` AS
    select 
        `m_tag`.`id` AS `id`,
        `m_tag`.`tag` AS `tag`,
        count(`m_article`.`id`) AS `cnt`
    from
        ((`m_tag`
        left join `r_article_tag` ON ((`m_tag`.`id` = `r_article_tag`.`tag_id`)))
        left join `m_article` ON (((`m_article`.`id` = `r_article_tag`.`article_id`)
            and (`m_article`.`publish_flg` in ('1' , '2')))))
    where
        ((`m_tag`.`del_flg` = 0)
            and (`r_article_tag`.`del_flg` = '0'))
    group by `m_tag`.`id` , `m_tag`.`tag`