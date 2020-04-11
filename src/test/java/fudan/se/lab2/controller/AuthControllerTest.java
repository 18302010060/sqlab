package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.Authority;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.service.AuthService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthControllerTest {
    private UserRepository userRepository=new UserRepository() {
        @Override
        public User findByUsername(String username) {
            return null;
        }

        @Override
        public User findByFullname(String fullname) {
            return null;
        }

        @Override
        public <S extends User> S save(S entity) {
            return null;
        }

        @Override
        public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<User> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public List<User> findAll() {
            return null;
        }

        @Override
        public Iterable<User> findAllById(Iterable<Long> longs) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(User entity) {

        }

        @Override
        public void deleteAll(Iterable<? extends User> entities) {

        }

        @Override
        public void deleteAll() {

        }
    };
    private AuthorityRepository authorityRepository=new AuthorityRepository() {
        @Override
        public Authority findByAuthority(String authority) {
            return null;
        }

        @Override
        public <S extends Authority> S save(S entity) {
            return null;
        }

        @Override
        public <S extends Authority> Iterable<S> saveAll(Iterable<S> entities) {
            return null;
        }

        @Override
        public Optional<Authority> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public Iterable<Authority> findAll() {
            return null;
        }

        @Override
        public Iterable<Authority> findAllById(Iterable<Long> longs) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(Authority entity) {

        }

        @Override
        public void deleteAll(Iterable<? extends Authority> entities) {

        }

        @Override
        public void deleteAll() {

        }
    };

    AuthService authService=new AuthService(userRepository,authorityRepository);
    AuthController authController=new AuthController(authService);
    @Test
    void register() {
        RegisterRequest request =new RegisterRequest("asdqwe","qwe123","1805233@123.com","fudan","shanghai","Mary");
        RegisterRequest request1 =new RegisterRequest("asdqwe","qwe123","18052313@123.com","fudan","shanghai","Mary");

        LoginRequest request2=new LoginRequest("asdqw","qwe123");
        LoginRequest request3=new LoginRequest("dqwe","qwe123");

        assertEquals("<200 OK OK,true,[]>",authController.register(request).toString());
        assertEquals("<200 OK OK,true,[]>",authController.register(request1).toString());
        assertEquals("<200 OK OK,false,[]>",authController.register(request).toString());






    }

    @Test
    void login() {
    }
}