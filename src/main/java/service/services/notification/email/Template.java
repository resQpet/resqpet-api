package service.services.notification.email;

import lombok.Getter;

@Getter
public enum Template {

    ACCOUNT_RECOVER("account-recover");

    private final String template;

    Template(String template) {
        this.template = template;
    }
}
