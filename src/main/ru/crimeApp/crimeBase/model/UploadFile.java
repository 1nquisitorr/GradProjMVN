package crimeApp.crimeBase.model;

import javax.persistence.*;

@Entity
@Table(name = "FILES_UPLOAD")
public class UploadFile {
    @Id
    @Column(name = "upload_id")

    private int id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_data")
    private byte[] data;


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}