package org.onecell.example.jpa.entity.etc;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
@Table(name="WTWS_FILE_INFO")
public class FileInfo implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="FILE_INFO_SEQ")
    @TableGenerator(
            name="FILE_INFO_SEQ",
            table="WTWS_SEQ_GENERATION",
            pkColumnName = "KEY_NAME",
            valueColumnName = "NEXT_VALUE",
            pkColumnValue="FILE_INFO_SEQ",allocationSize = 1,initialValue = 1
    )
    @Column(name = "ID")
    private Long id;


    @Column(name = "SAVED_FILE_NM")
    private String saved_file_nm;


    @Nationalized
    @Column(name = "SAVED_DIR_DC")
    private String saved_dir_dc;

    @Nationalized
    @Column(name = "ORIGIN_PATH")
    private String origin_path;

    @Column(name = "IS_FOLDER_AT")
    private Boolean is_folder_at;

    @Column(name = "SIZE_VA")
    private long size_va;

    @Column(name = "SAVED_DT")
    private Calendar saved_dt;

    @Column(name = "IS_PUBLISH_AT")
    private Boolean is_publish_at;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSaved_file_nm() {
        return saved_file_nm;
    }

    public void setSaved_file_nm(String saved_file_nm) {
        this.saved_file_nm = saved_file_nm;
    }


    public String getSaved_dir_dc() {
        return saved_dir_dc;
    }

    public void setSaved_dir_dc(String saved_dir_dc) {
        this.saved_dir_dc = saved_dir_dc;
    }

    public String getOrigin_path() {
        return origin_path;
    }

    public void setOrigin_path(String origin_path) {
        this.origin_path = origin_path;
    }

    public Calendar getSaved_dt() {
        return saved_dt;
    }

    public void setSaved_dt(Calendar saved_dt) {
        this.saved_dt = saved_dt;
    }

    public Boolean getIs_publish_at() {
        return is_publish_at;
    }

    public void setIs_publish_at(Boolean is_publish_at) {
        this.is_publish_at = is_publish_at;
    }

    public Boolean getIs_folder_at() {
        return is_folder_at;
    }

    public void setIs_folder_at(Boolean is_folder_at) {
        this.is_folder_at = is_folder_at;
    }

    public long getSize_va() {
        return size_va;
    }

    public void setSize_va(long size_va) {
        this.size_va = size_va;
    }
}
