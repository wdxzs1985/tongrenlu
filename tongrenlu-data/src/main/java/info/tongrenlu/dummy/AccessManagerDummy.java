package info.tongrenlu.dummy;

import info.tongrenlu.entity.AccessEntity;
import info.tongrenlu.jdbc.AccessManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AccessManagerDummy extends AccessManager {

    @Override
    public List<AccessEntity> findByArticleId(final String articleId) {
        final List<AccessEntity> result = new ArrayList<AccessEntity>();

        for (int i = 0; i < DummyHelper.nextInt(100); i++) {
            final AccessEntity entity = new AccessEntity(articleId,
                                                         DummyHelper.randomUserId());
            result.add(entity);
        }
        return result;
    }
}
