package cn.toseektech.example;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;

public class FriendDemo {

	private static final String FILE_PATH = "F:/friend.txt";

	private static final Set<RelationShip> EXISTED_RELATION_SHIPS = new HashSet<>();

	private static final List<Set<String>> FRIENDS_LIST = new ArrayList<>();

	public static void main(String[] args) {

		List<String> readLines = FileUtil.readLines(new File(FILE_PATH), Charset.defaultCharset());

		readLines.forEach(line -> {
			String[] names = StrUtil.split(line, " ");
			String mainName = names[0];
			Set<String> friends = new HashSet<>();
			for (int i = 1; i < names.length; i++) {
				EXISTED_RELATION_SHIPS.add(new RelationShip(mainName, names[i]));
				friends.add(names[i]);
			}
			FRIENDS_LIST.add(friends);
		});

		Map<RelationShip, Integer> result = new HashMap<>();
		
		FRIENDS_LIST.forEach(friends -> {
			List<String> names = new ArrayList<>(friends);
			for (int i = 0; i < names.size(); i++) {
				for (int j = i + 1; j < names.size(); j++) {
					RelationShip newRelationShip = new RelationShip(names.get(i), names.get(j));
					if (!EXISTED_RELATION_SHIPS.contains(newRelationShip)) {
						result.compute(newRelationShip, (key, oldValue) -> oldValue == null ? 1 : ++oldValue);
					}
				}
			}
		});			
		Console.log(result);
	}

}

class RelationShip {

	private String mainName;

	private String subName;

	public RelationShip(String mainName, String subName) {
		this.mainName = mainName;
		this.subName = subName;
	}

	public String getMainName() {
		return mainName;
	}

	public String getSubName() {
		return subName;
	}

	@Override
	public int hashCode() {
		return mainName.hashCode() + subName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RelationShip) {
			RelationShip other = RelationShip.class.cast(obj);
			return StringUtils.equals(other.mainName, mainName) && StringUtils.equals(other.subName, subName)
					|| StringUtils.equals(other.mainName, subName) && StringUtils.equals(other.subName, mainName);
		}
		return false;
	}

	@Override
	public String toString() {
		return mainName + "-" + subName;
	}
}
