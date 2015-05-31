package models;

import java.util.Date;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;
import java.sql.Blob;

import javax.persistence.Lob;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.avaje.ebean.annotation.CreatedTimestamp;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Images extends Model {
    @Id
    public Long id;
    public String imageName;
    @ManyToOne
    public Users user;
    @Lob
    public byte[] file;
    @CreatedTimestamp
    public Date createdDate;

    public static Finder<Long, Images> find = new Finder(Long.class, Images.class);

    public Images(File file, String imageName, Users user) throws Exception{
	InputStream imageInputStream = new FileInputStream(new File(file.toString()));

	this.file = getByteArray(imageInputStream);
	this.imageName = imageName;
	this.user = user;
    }

    public static byte[] getByteArray(InputStream is) throws Exception {
	ByteArrayOutputStream b = new ByteArrayOutputStream();
	BufferedOutputStream os = new BufferedOutputStream(b);
	while(true) {
	    int i = is.read();
	    if(i == -1) break;
	    os.write(i);
	}
	os.flush();
	os.close();
	return b.toByteArray();
    }
}
