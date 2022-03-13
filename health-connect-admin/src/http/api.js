/* 
 * 接口统一集成模块
 */
import * as login from './moudules/login'
import * as user from './moudules/user'
import * as dept from './moudules/dept'
import * as role from './moudules/role'
import * as menu from './moudules/menu'
import * as dict from './moudules/dict'
import * as log from './moudules/log'
import * as sys from './moudules/jvm'
import * as blog from './moudules/blog'
import * as star from './moudules/star'
import * as forward from './moudules/forward'
import * as comment from './moudules/comment'
import * as tag from './moudules/tag'
import * as billboard from './moudules/billboard'
import * as view from './moudules/view'
import * as tip from './moudules/tip'
import * as promotion from './moudules/promotion'
import * as series from './moudules/series'
import * as activity from './moudules/activity'
import * as chatMessage from './moudules/chatMessage'
import * as sensitive from './moudules/sensitive'
import * as follow from './moudules/follow'
import * as image from './moudules/image'
import * as sysNotice from './moudules/sysNotice'
import * as upload from './moudules/upload'

// 默认全部导出
export default {
    login,
    user,
    dept,
    role,
    menu,
    dict,
    log,
    sys,
    blog,
    forward,
    star,
    comment,
    tag,
    billboard,
    view,
    tip,
    promotion,
    series,
    activity,
    chatMessage,
    sensitive,
    follow,
    image,
    sysNotice,
    upload
}