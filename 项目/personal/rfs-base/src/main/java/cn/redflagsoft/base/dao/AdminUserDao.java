package cn.redflagsoft.base.dao;

import java.util.List;

import cn.redflagsoft.base.bean.Clerk;

public interface AdminUserDao {
	
	List<Clerk> findAllAdminUsers();
}
