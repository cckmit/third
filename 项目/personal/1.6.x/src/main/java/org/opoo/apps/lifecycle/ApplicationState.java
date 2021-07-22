package org.opoo.apps.lifecycle;

/**
 * 应用状态。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5 
 */
public enum ApplicationState {
	/**
	 * 正在初始化。
	 */
	INITIALIZING("Initializing"),
	/**
	 * 初始化完成。
	 */
	INITIALIZED("Initialized"),
	/**
     * 安装开始。
     */
	SETUP_STARTED("Setup started"), 
	/**
	 * 安装完成。
	 */
	SETUP_COMPLETE("Setup complete"), 
	/**
	 * 迁移开始。
	 */
	MIGRATION_STARTED("Migration started"), 
	/**
	 * 迁移完成。
	 */
	MIGRATION_COMPLETE("Migration complete"), 
	/**
	 * 需要升级。
	 */
	UPGRADE_NEEDED("Upgrade needed"), 
	/**
	 * 升级开始。
	 */
	UPGRADE_STARTED("Upgrade started"), 
	/**
	 * 升级完成。
	 */
	UPGRADE_COMPLETE("Upgrade complete"), 
	/**
	 * 维护开始。
	 */
	MAINTENANCE_STARTED("Maintenance started"), 
	/**
	 * 维护结束。
	 */
	MAINTENANCE_COMPLETE("Maintenance complete"), 
	/**
	 * 运行中。
	 */
	RUNNING("Running"), 
	/**
	 * 系统错误。
	 */
	ERROR("Error"), 
	/**
	 * 系统已关闭。
	 */
	SHUTDOWN("Shutting-down"), 
	/**
	 * 需要重启。
	 */
	RESTART_REQUIRED("Restart required."), 
	POST_UPGRADE_STARTED("Post upgrade started"), 
	POST_UPGRADE_NEEDED("Post upgrade needed"), 
	POST_UPGRADE_COMPLETE("Post upgrade complete");

	private final String desc;

	private ApplicationState(String description) {
		this.desc = description;
	}

	public String toString() {
		return desc;
	}
}
