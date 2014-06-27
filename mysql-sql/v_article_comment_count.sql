CREATE 
VIEW `v_article_comment_count` AS
    select 
        `m_comment`.`article_id` AS `articleId`,
        count(`m_comment`.`id`) AS `cnt`
    from
        `m_comment`
    group by `m_comment`.`article_id`