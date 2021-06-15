package org.onecell.example.jpa.entity.etc;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WTWS_LOCK")
public class Lock {
    @Id
    @Column(name = "LOCK_NAME")
    private String lock_name;

    public String getLock_name() {
        return lock_name;
    }

    public void setLock_name(String lock_name) {
        this.lock_name = lock_name;
    }
}
