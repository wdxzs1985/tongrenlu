package info.tongrenlu.dummy;

import info.tongrenlu.entity.CommentEntity;
import info.tongrenlu.jdbc.CommentManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CommentManagerDummy extends CommentManager {

    @Override
    public List<CommentEntity> findByArticleId(final String articleId) {
        final List<CommentEntity> result = new ArrayList<CommentEntity>();
        for (int i = 0; i < DummyHelper.nextInt(10); i++) {
            final CommentEntity entity = new CommentEntity(DummyHelper.genCommentId(),
                                                           articleId,
                                                           DummyHelper.randomUserId(),
                                                           "Comment");
            result.add(entity);
        }

        return result;
    }
}
