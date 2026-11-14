package com.google.android.accessibility.selecttospeak

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

/**
 * 伪装成 Google Select-to-Speak 的辅助功能服务（LinkGo 同款方案）。
 *
 * 用于绕过应用（如微信）对第三方无障碍服务的辅助功能限制，获取完整的节点文本与
 * 精确的字符坐标：微信等应用会按无障碍服务的组件身份放行或限制，Google 官方服务
 * 身份在白名单内。
 *
 * 本服务不承载任何业务逻辑，仅作为链接雷达全屏扫描的宿主——
 * LinkRadarOverlay 通过 [getInstance] 取得本实例执行全屏扫描。
 * 需要在系统无障碍设置中与本应用的主服务一同启用；未启用时雷达自动回退主服务
 * （QQ 等不做限制的应用不受影响）。
 */
class SelectToSpeakService : AccessibilityService() {

    companion object {
        @Volatile
        private var instance: SelectToSpeakService? = null

        fun getInstance(): SelectToSpeakService? = instance
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onUnbind(intent: Intent?): Boolean {
        if (instance === this) instance = null
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        if (instance === this) instance = null
        super.onDestroy()
    }
}
