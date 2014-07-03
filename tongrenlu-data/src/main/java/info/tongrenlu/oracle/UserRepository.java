package info.tongrenlu.oracle;

import info.tongrenlu.oracle.entity.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
