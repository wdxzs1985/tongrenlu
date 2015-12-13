CREATE 
VIEW `v_article_access_count` AS
    select 
        `m_access`.`article_id` AS `articleId`,
        count(`m_access`.`id`) AS `cnt`
    from
        `m_access`
    group by `m_access`.`article_id`;
