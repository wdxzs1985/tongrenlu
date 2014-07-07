package info.tongrenlu.dummy;

import info.tongrenlu.entity.FollowEntity;
import info.tongrenlu.jdbc.FollowManager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class FollowManagerDummy extends FollowManager {

    @Override
    public List<FollowEntity> findByUserId(final String userId) {
        final List<FollowEntity> result = new ArrayList<FollowEntity>();

        for (int i = 0; i < DummyHelper.nextInt(100); i++) {
            final FollowEntity entity = new FollowEntity(DummyHelper.randomUserId(),
                                                         userId);
            result.add(entity);
        }

        return result;
    }
}
