package service.services.auth.account;

import service.domain.models.account.PasswordRecoverRequest;

public interface AccountService {

    void sendAccountRecover(String login);

    boolean isRecoverTokenValid(String token);

    void recover(PasswordRecoverRequest request);

}
