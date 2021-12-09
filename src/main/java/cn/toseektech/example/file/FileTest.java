package cn.toseektech.example.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.StandardOpenOption;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Objects;

import cn.hutool.core.lang.Snowflake;

public class FileTest {

	public static void main(String[] args) throws IOException {
		Indexes indexes = new Indexes("D:/indexes");
		Datas datas = new Datas("D:/datas");
		String name ="张三-";
		Snowflake snowflake = new Snowflake(1,1);
		for(Long i =0L ;i<100L;i++) {
			Long id = i;
			Location location = datas.saveDate((name+id).getBytes());
			Index index = new Index(id,location.getPosition(),location.getSize());
			indexes.save(index);
		}
		Index index = indexes.read(15L);
		System.out.println(new String(datas.readDate(index.getPosition(), index.getSize())));
       
	}

}

class Index {

	private Long id;

	private Long position;

	private Integer size;


	public Index(Long id, Long position, Integer size){
		this.id = id;
		this.position = position;
		this.size = size;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public ByteBuffer[] serialize() throws IOException {
		return new ByteBuffer[] { 
				ByteBuffer.wrap(Util.longToByteArray(id)),
				ByteBuffer.wrap(Util.longToByteArray(position)), 
				ByteBuffer.wrap(Util.intToByteArray(size)),
			};
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}

class Indexes {
	

	private FileChannel writeFileChannel;

	private FileChannel readFileChannel;

	public Indexes(String indexFilePath) {

		try {
			writeFileChannel = FileChannel.open(new File(indexFilePath).toPath(), StandardOpenOption.APPEND);
			readFileChannel = FileChannel.open(new File(indexFilePath).toPath(), StandardOpenOption.READ);
		} catch (IOException e) {

		}
	}

	public void save(Index index) throws IOException {
		writeFileChannel.write(index.serialize());
	}

	public Index read(Long id) throws IOException {
		Long space = 20 * 100000L;
		Long fileSize = readFileChannel.size();
		Long position = 0L;

		do {
			if (position + space > fileSize) {
				space = fileSize - position;
			}
			MappedByteBuffer map = readFileChannel.map(MapMode.READ_ONLY, position, space);
			for (Long i = space-20L; i > 0 ; i = i-20L) {
				//如果查询的id比最后一个id还大，name直接跳到下一个数据区
				long dataId = map.getLong(i.intValue());
				if(id > dataId) {
					break;
				}
				if (Objects.equal(id, dataId)) {
					long dataPosition = map.getLong(i.intValue() + 8);
					int dataSize = map.getInt(i.intValue() + 16);
					return new Index(id, dataPosition, dataSize);
				}
			}
			position += space;
		} while (position < fileSize);
		return null;
	}

}

class Datas {
	
	private FileChannel writeFileChannel;

	private RandomAccessFile randomAccessFile;
	
	public Datas(String indexFilePath) {		
		try {
			writeFileChannel = FileChannel.open(new File(indexFilePath).toPath(), StandardOpenOption.APPEND);
			randomAccessFile =new RandomAccessFile(indexFilePath,"r");
		} catch (IOException e) {

		}
	}
	
		
	public Location saveDate(byte[] data) throws IOException {
		Location location = new Location();
		location.setPosition(writeFileChannel.position());
		location.setSize(data.length);
		writeFileChannel.write(ByteBuffer.wrap(data));
		return location;
	}
	
    public byte[]  readDate(Long position, int size) throws IOException {
    	randomAccessFile.seek(position);
    	byte[] bytes =new byte[size];
    	randomAccessFile.read(bytes);
    	return bytes;
	}

}


class Location{
	
	private Long position;

	private Integer size;

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}

class Util {

	private Util() {
	}

	public static long byteArrayToLong(byte[] data) throws IOException {
		ByteArrayInputStream bai = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bai);
		return dis.readLong();
	}

	// byte数组转换成int
	public static int byteArrayToInt(byte[] data) throws IOException {
		ByteArrayInputStream bai = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bai);
		return dis.readInt();
	}

	// long转换成byte数组
	public static byte[] longToByteArray(long l) throws IOException {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bao);
		dos.writeLong(l);
		return bao.toByteArray();
	}

	// int转换成byte数组
	public static byte[] intToByteArray(int a) throws IOException {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bao);
		dos.writeInt(a);
		return bao.toByteArray();
	}
}
