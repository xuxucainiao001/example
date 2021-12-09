package cn.toseektech.example.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseDemo {

	public static void main(String[] args) throws IOException {
		Connection connection = createHbase();
		insertOrUpdateData(connection, "order", "rk1", "detail", "price", "500");
		insertOrUpdateData(connection, "order", "rk1", "detail", "amount", "1");
		insertOrUpdateData(connection, "order", "rk1", "info", "type", "0");
		insertOrUpdateData(connection, "order", "rk2", "detail", "price", "300");
		insertOrUpdateData(connection, "order", "rk2", "detail", "amount", "5");
		//deleteData(connection, "order", "rk1", "info", null);
		//deleteData(connection, "order", "rk2", "detail", "amount");
		selectData(connection,"order","rk1");
		connection.close();
	}
	

	/**
	 * 创建Hbase连接
	 * 
	 * @return
	 * @throws IOException
	 */
	public static Connection createHbase() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "47.99.60.114:2181,120.27.219.207:2181,47.97.251.36:2181");
		conf.set("hbase.master", "47.99.60.114:9000");
		return ConnectionFactory.createConnection(conf);
	}
	
	/**
	 * 查询数据
	 * @param connection
	 * @param tableName
	 * @param rowKey
	 * @throws IOException 
	 */
	public static void selectData(Connection connection, String tableName, String rowKey) throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Get get = new Get(rowKey.getBytes());
		Result result = table.get(get);
		List<Cell> listCells = result.listCells();
		listCells.forEach(cell->{
			String familyName = Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength());
			String key = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
			String value = Bytes.toString(cell.getValueArray(),cell.getValueOffset(),cell.getValueLength());
			System.out.println(rowKey+"=>{"+familyName+":"+key+":"+value+"}");
		});
		table.close();
	}
    
	/**
	 * 删除数据
	 * @param connection
	 * @param tableName
	 * @param rowKey
	 * @param familyName
	 * @param key
	 * @throws IOException
	 */
	public static void deleteData(Connection connection, String tableName, String rowKey, String familyName, String key)
			throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Delete del = new Delete(rowKey.getBytes());
		if(familyName!=null) {
			del.addFamily(familyName.getBytes());
			if(key!=null) {
				del.addColumns(familyName.getBytes(), key.getBytes());
			}
		}				
		table.delete(del);
		table.close();

	}

	/**
	 * 插入数据或修改数据
	 * 
	 * @param connection
	 * @param tableName
	 * @param rowKey
	 * @param familyName
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public static void insertOrUpdateData(Connection connection, String tableName, String rowKey, String familyName,
			String key, String value) throws IOException {
		Table table = connection.getTable(TableName.valueOf(tableName));
		Put put = new Put(rowKey.getBytes());
		put.addColumn(familyName.getBytes(), key.getBytes(), value.getBytes());
		// 如果列族不存在
		if (!table.getDescriptor().hasColumnFamily(familyName.getBytes())) {
			Admin admin = connection.getAdmin();
			// 要删除表或改变其设置，首先需要使用 disable 命令关闭表
			admin.disableTable(TableName.valueOf(tableName));
			admin.addColumnFamily(TableName.valueOf(tableName), ColumnFamilyDescriptorBuilder.of(familyName));
			// 操作完成后开启表
			admin.enableTable(TableName.valueOf(tableName));
			admin.close();
		}
		table.put(put);
		table.close();
	}

	/**
	 * 创建表
	 * 
	 * @param connection
	 * @param tableName
	 * @param columFamilyName
	 * @throws IOException
	 */
	public static void createHTable(Connection connection, String tableName, String columFamilyName)
			throws IOException {
		Admin admin = connection.getAdmin();
		admin.createTable(TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName))
				.setColumnFamily(ColumnFamilyDescriptorBuilder.of(columFamilyName)).build());
		admin.close();
	}

}
