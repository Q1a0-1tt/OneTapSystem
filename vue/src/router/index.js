import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
    {path: '/login', name:'login', component:() =>import('../views/Login.vue')},
    {path: '/register', name:'Register', component:() =>import('../views/Register.vue')},
    //{path: '/404', name:'Register', component:() =>import('../views/404.vue')},
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export const setRoutes = () => {
   const storeMenus = localStorage.getItem("menus");
   if(storeMenus){
       const managerRoute = {path:'/',name :'Manage',component:()=>import('../views/Manage.vue'),redirect:'/login',children:[
               {path: '/person', name:'person', component:() =>import('../views/Person.vue')},
           ]}
       const menus = JSON.parse(storeMenus)
       menus.forEach(item=>{
           if(item.path){
               let itemMenu = {path:item.path,name:item.name,component:()=>import('../views/'+item.pagePath+'.vue')}
               managerRoute.children.push(itemMenu)
           }else if(item.children.length){
               item.children.forEach(item=>{
                   if(item.path){
                       let itemMenu = {path:item.path,name:item.name,component:()=>import('../views/'+item.pagePath+'.vue')}
                       managerRoute.children.push(itemMenu)
                   }
               })
           }
       })
        const CurrentrRouteName = router.getRoutes().map(v=>v.name)
       if(!CurrentrRouteName.includes('Manage')){
           router.addRoute(managerRoute)
       }

   }
}

setRoutes()

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
router.beforeEach((to,from,next)=>{
    localStorage.setItem("currentPathName",to.name)
    store.commit('setPath')
    const storeMenu = localStorage.getItem("menus")
    if(!to.matched.length){
        if(storeMenu){
            next("/404")
        }else{
            next("/login")
        }
    }
    next()

})
export default router
