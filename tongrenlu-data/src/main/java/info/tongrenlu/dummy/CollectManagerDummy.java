package info.tongrenlu.dummy;

import info.tongrenlu.entity.CollectEntity;
import info.tongrenlu.jdbc.CollectManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CollectManagerDummy extends CollectManager {

    @Override
    public List<CollectEntity> findByArticleId(final String articleId) {
        final List<CollectEntity> result = new ArrayList<CollectEntity>();

        for (int i = 0; i < DummyHelper.nextInt(10); i++) {
            final CollectEntity entity = new CollectEntity(articleId,
                                                           DummyHelper.randomUserId());
            result.add(entity);
        }

        return result;
    }
}
