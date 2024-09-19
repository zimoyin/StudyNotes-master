# 我的世界MOD开发笔记

*==——作者：子墨==*

*==——时间：2021/12/04==*

参考： [Harbinger](https://harbinger.covertdragon.team/)  是一套全面的，基于 Forge 的，中文 Minecraft Mod 开发指南。

参考： [ IDF 框架]( https://github.com/IdeallandEarthDept/IdeallandFramework)  他是基于 mc1.12 封装的框架

## 零、环境

其实Mod开发很多都是在第一步被劝退，因为mc mod的开发环境搭建有些困难，通常会因为各种因为各种原因导致构建失败，从而使开发者卡在第一步开发环境的搭建上。开发环境的搭建我见过很多方法，也试过很多方法，有成功有失败。但是过程都不太顺利，要么环境构建两三个小时后因为网速问题而失败，要么因为其他原因而失败。总之搭建环境的过程都不太顺利。



开发环境: java 1.8

开发工具：IDEA

环境构建工具：~~IDEA 插件 {Minecraft Development}~~ IDF 框架 https://github.com/IdeallandEarthDept/IdeallandFramework



## 一、初始工程

1. 运行 [1_其一_setupDecomp_请先运行此脚本_再打开IDE.bat](1_%E5%85%B6%E4%B8%80_setupDecomp_%E8%AF%B7%E5%85%88%E8%BF%90%E8%A1%8C%E6%AD%A4%E8%84%9A%E6%9C%AC_%E5%86%8D%E6%89%93%E5%BC%80IDE.bat)
2. IDEA 打开此项目

## 二、配置 MOD

### 1. 编辑 [mcmod.info](src%2Fmain%2Fresources%2Fmcmod.info)

> modid: MOD的ID，请保持此ID在世界上的唯一(可以为字母),如果和其他MOD一致会导致mod冲突。此ID对于用户非可见  
> name： MOD的名称  
> description: mod 的描述  
> url： mod的地址
> authorList： 作者列表  
> credits： 致谢  
> 其余：请不要改动

### 2. 编辑代码包名(一定要改否则会冲突)
> com.somebody 可以改成自己的包名 -> com.zimoyin.你的modid  
> resources/assets.idlframework -> resources/assets.你的modid  

### 3. 修改代码 
   * github.zimoyin.mymod.IdlFramework 
   > public static final String MODID = "你的modid";  
   > public static final String NAME = "你mod的名称";  

* github.zimoyin.mymod.util.Reference

> 不修改导致的问题(建议观看): https://www.bilibili.com/read/cv17173344
>
> // 对应的是项目里面  ClientProxy 类的路径，注意修改就行，懂得变通
>
> CLIENT_PROXY_CLASS = "com.作者名.模组ID.proxy.ClientProxy"; 
>
> // 对应的是项目里面  ServerProxy 类的路径，注意修改就行，懂得变通
>
> SERVER_PROXY_CLASS = "com.作者名.模组ID.proxy.ServerProxy"; 

## 三、启动

启动有服务端和客户端两种模式

### 1. 客户端

添加启动配置，Application 

![image-20221229134535855](mod%E5%BC%80%E5%8F%91.assets/image-20221229134535855.png)

![image-20221229134608662](mod%E5%BC%80%E5%8F%91.assets/image-20221229134608662.png)

![image-20221229134737107](mod%E5%BC%80%E5%8F%91.assets/image-20221229134737107.png)



* 启动后，找到MOD列表中的自己的MOD

![image-20221229140741080](mod%E5%BC%80%E5%8F%91.assets/image-20221229140741080.png)



### 2. 无法加载 mcmod.info || 没有贴图

![image-20230201230342366](mod%E5%BC%80%E5%8F%91.assets/image-20230201230342366.png)

```json
 在 IDEA 的 设置 -- 构建、执行、部署 -- 构建工具 -- Gradle 里把「使用此工具构建和运行」和「使用此工具运行测试」都改成「Gradle （默认）」，
然后在 build.gradle (末尾添加，否则gradle报错)里添加以下内容：
sourceSets {
    main {
        output.resourcesDir = file('build/combined')
        java.outputDir = file('build/combined')
    }
}
```



## 四、添加附魔（简单尝试）

* github.zimoyin.mymod.enchantments.ModEnchantmentInit

```java
// 这是这个类中的示例代码
public static final ModEnchantmentBase ANTI_VANILLA = new ModEnchantmentBase(
    //附魔ID
    "mymod.enchantment",
    //稀有度
    Enchantment.Rarity.UNCOMMON,
    //附魔类型: 对哪些物品进行附魔
    EnumEnchantmentType.WEAPON,
    //有效装备栏：主手附魔
    mainHand
);
```

* assets.mymod.lang 语言文件夹

  * en_us.lang 英文名

  ```properties
  # enchantment.附魔ID=英文名称
  enchantment.mymod.enchantment=Early Access
  ```

  * zh_cn.lang 中文名

  ```properties
  # enchantment.附魔ID=中文名称
  enchantment.mymod.enchantment=我的第一个附魔
  ```

![image-20221229144721145](mod%E5%BC%80%E5%8F%91.assets/image-20221229144721145.png)

* 如果不修改 assets.mymod.lang 语言文件夹 

```properties
# 显示
# enchantment.附魔ID
enchantment.mymod.enchantment
```



![image-20221229144858042](mod%E5%BC%80%E5%8F%91.assets/image-20221229144858042.png)

### 如何让自己的附魔有效果？

通常，附魔的效果和附魔本身的类没有关系。举个例子，精准采集和时运的逻辑实际上是在 `Block` 里的。还记得 Forge patch 后的那个获得掉落的方法 `getDrops` 吗？它最后一个 `int` 参数就是当前使用工具的时运等级。
这也意味着，你的附魔的具体效果需要通过覆写 `Block` 或 `Item` 类下的某些方法及事件订阅等方式实现。`EnchantmentHelper` 类提供了一些帮助确定某物品的附魔等级的方法，比如 `getEnchantmentLevel`（`func_77506_a`）和 `getEnchantments`（`func_82781_a`）。

## 五、制作物品(简单尝试)

注：mc中 f3+H：可以看到物品详细信息

### 1. 添加物品或者方块

* 语言文件：添加物品名称  `*.lang`

```properties
#item 物品
#item.物品ID.name  添加物品
item.test.name=我的物品
#item.物品ID.desc  添加物品的描述
item.test.desc=我的物品的描述

#tile 方块
#tile.物品ID.name  添加方块
tile.testblock.name=我的方块
#方块不支持描述
```

* 代码文件: 注册物品 `github.zimoyin.mymod.item.ModItems`

```java
// 参数为物品的ID，注意要与语言文件的物品ID一致
public static final Item TEST = new ItemBase("test");
```

* 代码文件：注册方块 `github.zimoyin.mymod.blocks.ModBlocks`

```java
//参数(物品ID,Material.质地之一)
//方块会自动去注册物品
public static final Block TEST = new BlockBase("testblock", Material.CLAY)
    //			.setCreativeTab(ModCreativeTab.IDL_MISC) //放置在哪个标签页下，可以不写
    //          .setHardness(15f)// 方块硬度，可以不写
    ;
```

### 2. 添加贴图

* 物品没有会是紫黑方块的模样

![image-20230101143615313](mod%E5%BC%80%E5%8F%91.assets/image-20230101143615313.png)

* 贴图要求

> 1. 尺寸	
>    * 16x16 (推荐) 、32x32  、贴图只能是2的整数次幂 再如 64x64、贴图越大资源消耗越大
> 2. 贴图保存位置
>    * 方块贴图位置: textures/blocks
>    * 物品贴图位置: textures/items/类别
> 3. 贴图名称
>    * 贴图类型/后缀:  *.png
>    * 贴图名称: 与物品ID一致

* 操作

制作的贴图分放入了`assets.mymod.textures.blocks`、 `assets.mymod.textures.items.misc`



### 3.修改JSON

* 物品需要一个JSON，方块需要三个JSON。修改较多仅此IDF提供了 lua 脚本

* 配置LUA脚本 

GenBlockJson.lua

```
1. 修改文件中的 modName="你的MODID"
2. 根据需要在lua文件底部添加代码
```

![image-20230101150327674](mod%E5%BC%80%E5%8F%91.assets/image-20230101150327674.png)

```lua
GenItem("misc", "test");
GenBlock("testblock");
```



### 4. 效果

![image-20230101153937142](mod%E5%BC%80%E5%8F%91.assets/image-20230101153937142.png)

* 所有物品在此标签页是因为IDF代码设置原因

![image-20230101154233775](mod%E5%BC%80%E5%8F%91.assets/image-20230101154233775.png)

![image-20230101154311645](mod%E5%BC%80%E5%8F%91.assets/image-20230101154311645.png)



### 5. 盾牌

从 https://github.com/MinecraftForge/MinecraftForge/pull/4126 合并之后，我们可以自定义盾牌了。

```java
@Override
public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
    // 注意它传入了盾牌本身和持盾的实体。（注意到持盾的不一定是玩家！）
    // 换言之，你可以在这个时候对这玩意搞各种事情
    return true;
}
```

## 六、合成配方（简单尝试）

`assets.mymod.recipes.*.json` : json文件名称任意

注：mc中 f3+H：可以看到物品详细信息

### 1.有序配方

`assets.mymod.recipes.*.json` : json文件名称任意

![image-20230101181201066](mod%E5%BC%80%E5%8F%91.assets/image-20230101181201066.png)

### 2.无序配方

`assets.mymod.recipes.*.json` : json文件名称任意

![image-20230101180831879](mod%E5%BC%80%E5%8F%91.assets/image-20230101180831879.png)

![image-20230101180719770](mod%E5%BC%80%E5%8F%91.assets/image-20230101180719770.png)



### 3.meta

![image-20230101184003481](mod%E5%BC%80%E5%8F%91.assets/image-20230101184003481.png)

![image-20230101184049903](mod%E5%BC%80%E5%8F%91.assets/image-20230101184049903.png)



### 4. 常见错误

![image-20230101184149191](mod%E5%BC%80%E5%8F%91.assets/image-20230101184149191.png)

![image-20230101184242250](mod%E5%BC%80%E5%8F%91.assets/image-20230101184242250.png)

## 七、烧制配方(简单尝试)

### 1.代码: 方式一

* github.zimoyin.mymod.init.ModRecipes
* 注：mc中 f3+H：可以看到物品详细信息

![image-20230102140138633](mod%E5%BC%80%E5%8F%91.assets/image-20230102140138633.png)

```java
	
	public static void Init() {
		//Only smelting recipes
		GameRegistry.addSmelting(Items.STICK, new ItemStack(ModItems.TEST), 0.1f);
//		GameRegistry.addSmelting(Items.STICK, new ItemStack(Items.BIRCH_DOOR,个数), 0.1f);//木棍烧成烈焰棒
		GameRegistry.addSmelting(Blocks.STONE, new ItemStack(Items.BIRCH_DOOR), 0.1f);

	}
```



![image-20230102140731282](mod%E5%BC%80%E5%8F%91.assets/image-20230102140731282.png)

![image-20230102140807188](mod%E5%BC%80%E5%8F%91.assets/image-20230102140807188.png)

* 只要是在mc中注册过的物品都能被调用（烧制

### 2. 物品代码引用

![image-20230102141125773](mod%E5%BC%80%E5%8F%91.assets/image-20230102141125773.png)

### 3. ItemStack是什么

![image-20230102141458612](mod%E5%BC%80%E5%8F%91.assets/image-20230102141458612.png)

![image-20230102141736057](mod%E5%BC%80%E5%8F%91.assets/image-20230102141736057.png)

### 4. 例子

![image-20230102142201419](mod%E5%BC%80%E5%8F%91.assets/image-20230102142201419.png)

## 八、加载mod到运行环境

加载MOD到开发的运行环境中

将mod添加到 run/mods 下



## 九、实操：做一个大保健

### 1. 要求

![image-20230102142934601](mod%E5%BC%80%E5%8F%91.assets/image-20230102142934601.png)

### 2. 添加大保健

这里复用前面的代码和贴图，所以这就不写了。

要注意的是：

1. 要更改语言文件`*.lang` 如果没有填写或者填写不当（格式不对）就会显示物品ID甚至乱码
2. 修改代码，将物品添加到代码里面
3. 设计贴图（贴图可以到mc百科中去copy **“原版”** 的贴图，除非你有其他mod作者的授权否则就不要去copy其他mod的贴图）
4. 修改、添加json模型文件，这里使用了lua脚本进行

### 3.效果流畅图

* 执行流畅伪代码

![image-20230102143952241](mod%E5%BC%80%E5%8F%91.assets/image-20230102143952241.png)

![image-20230102144011288](mod%E5%BC%80%E5%8F%91.assets/image-20230102144011288.png)

### 4.注册物品

* 在`github.zimoyin.mymod.item.weapon`下新建个类

该类继承 `ItemSwordBase` 表示他是个剑

```java
public class ItemTestSword extends ItemSwordBase {
    public ItemTestSword(String name, ToolMaterial material) {
        super(name, material);
    }
}
```

* 在`github.zimoyin.mymod.item.ModItems` 中修改注册的代码

```java
//	public static final Item TEST = new ItemBase("test");
	/**
	 * 更改如下：
	 * 1. 注册自己的剑
	 * 2. 设置质地为钻石，这将继承钻石质地的特性，伤害等，并且可以被钻石修复
	 */
	public static final Item TEST = new ItemTestSword("test",Item.ToolMaterial.DIAMOND);
```

### 5.事件

* 将创建的剑类注册到事件总线中

```java
public class ItemTestSword extends ItemSwordBase {
    public ItemTestSword(String name, ToolMaterial material) {
        super(name, material);
        //添加当前类到事件总线
        CommonFunctions.addToEventBus(this);
        MinecraftForge.EVENT_BUS.register(this);//第二种写法
    }
}
```

* 监听事件

```java
//订阅事件
    @SubscribeEvent
    //参数：订阅生物受伤事件
    public void onAttack(LivingHurtEvent event) {
        //通过产生的事件中的生物来获取当前世界
        World world = event.getEntity().world;
        //world.isRemote： 世界是远程的
        //world.isRemote： True if the world is a 'slave' client; changes will not be saved or propagated from this world. For example, server worlds have this set to false, client worlds have this set to true.
        //world.isRemote： 如果世界是一个“受控制的”客户端，则为真；更改将不会从这个世界保存或传播。例如，服务器世界将此设置为false，客户端世界将此设为true。
        //如果是服务端就执行代码
        if (!world.isRemote) {
            //EntityLivingBase 生物的基类，包括：船，盔甲架,矿车等
            //受伤者
            EntityLivingBase hurtOne = event.getEntityLiving();
            //event.getSource().getTrueSource() 事件的.伤害来源.真正来源 ==> 返回 Entity，因为伤害不一定来自于生物也可能是仙人掌，火焰弹之类的
            //getTrueSource 真正来源: 如果没有这个代表伤害来自箭矢，而不是射出箭矢的攻击者
            //攻击者
            EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();

            if (
                    attacker != null//攻击者真实存在
                            && attacker.getHeldItemMainhand().getItem() == this //并且攻击者的物品类型是这个剑
            ) {
                attacker.heal(1f);//攻击者回复半颗心生命值
                //使用实体工具类添加Buff
                //EntityUtil.ApplyBuff(施加对象，效果，等级(索引从0开始)，秒数)
                EntityUtil.ApplyBuff(hurtOne, MobEffects.WITHER, 2, 2);//给对面凋零
                EntityUtil.ApplyBuff(attacker, MobEffects.REGENERATION, 2, 2);//给自己生命回复
                //原版写法
//                hurtOne.addPotionEffect(new PotionEffect(MobEffects.WITHER,2*20,2));

                //概率
                //产生随机数(0-1)，当随机数小于某个值就执行
                if (hurtOne.getRNG().nextFloat() < 0.5f) {
                    float amount = event.getAmount(); //攻击造成的伤害
                    //设置伤害翻倍
                    event.setAmount(amount * 2f);
                }
            }
        }
    }
```



## 十、打包成JAR

运行IDF下的 `打包前记得改版本号.bat` 他会在 build/lib 下产生



## 十一、事件与引雷附魔

* 事件：当某个事情发生的时候执行，如上面代码，当生物受伤时执行代码

![image-20230102155936021](mod%E5%BC%80%E5%8F%91.assets/image-20230102155936021.png)

![image-20230102161443880](mod%E5%BC%80%E5%8F%91.assets/image-20230102161443880.png)

![image-20230102161517623](mod%E5%BC%80%E5%8F%91.assets/image-20230102161517623.png)

![image-20230102161746206](mod%E5%BC%80%E5%8F%91.assets/image-20230102161746206.png)



* 引雷

![image-20230102160721701](mod%E5%BC%80%E5%8F%91.assets/image-20230102160721701.png)



## 十二、标签页

### 1.设置物品到特定标签页

```java
//添加物品
public static final Item TEST = new ItemTestSword("test",Item.ToolMaterial.DIAMOND)
			.setCreativeTab(ModCreativeTab.MY_TAB);//设置到自己的标签页
```



* github.zimoyin.mymod.item.ItemBase

```java
public ItemBase(String name)
{
    setUnlocalizedName(name);
    setRegistryName(name);
    //设置物品到默认标签页
    setCreativeTab(ModCreativeTab.IDL_MISC);

    ModItems.ITEMS.add(this);

    InitItem();
}
```



### 2.新建标签页

* github.zimoyin.mymod.init.ModCreativeTab

```java
public static final CreativeTabs MY_TAB = new CreativeTabs(CreativeTabs.getNextID(), "mytab") {
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem()
    {
        //物品图标
        return new ItemStack(ModBlocks.TEST);
    }
};
```

* *.lang

```properties
itemGroup.mytab=我的标签页
```



## 十三、生物属性修改（初步尝试）

### 1. 生物制作简要流程图

![image-20230103131746523](mod%E5%BC%80%E5%8F%91.assets/image-20230103131746523.png) 

### 2. 注册生物

![image-20230103135242789](mod%E5%BC%80%E5%8F%91.assets/image-20230103135242789.png)

![image-20230103135303925](mod%E5%BC%80%E5%8F%91.assets/image-20230103135303925.png)



#### 2.1 新建生物

* 新建 `github.zimoyin.mymod.entity.creatures.misc.EntitySkeletonWarrior` 
* 继承 `net.minecraft.entity.monster.EntitySkeleton`
* creatures目录 是IDF作者留下的各种例子

```java
public class EntitySkeletonWarrior extends EntitySkeleton {
    public EntitySkeletonWarrior(World worldIn) {
        super(worldIn);
    }
}
```



#### 2.2 注册生物

* github.zimoyin.mymod.entity.ModEntityInit **==>** registerEntities( )

```java
//直接注册实体
//        registerEntity("moroon_orbital_beacon", EntityMoroonBombBeacon.class);
//并指定刷怪蛋颜色
//        registerEntity("moroon_tainter", EntityMoroonTainter.class,0xff00ff, 0x000033);
//指定刷怪蛋颜色与视野范围
//        registerEntity("idealland_whitetower_core", EntityIDLWhiteTowerCore.class, ENTITY_NEXT_ID, 128, 0xeeee00, 0xffffff);

registerEntity("skeleton_warrior", EntitySkeletonWarrior.class,0xff00ff, 0x000033);
```



#### 2.3 *.lang

```properties
entity.skeleton_warrior.name=Skeleton Warrior
```



#### 2.4 生物燃烧

因为生物 继承自 `net.minecraft.entity.monster.AbstractSkeleton` 

里面有段代码会让生物在白天燃烧，可以把他覆盖重写，以此不让生物燃烧

```java
/**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
public void onLivingUpdate()
{
    //如果是白天，并且是服务器
    if (this.world.isDaytime() && !this.world.isRemote)
    {
        //获取亮度
        float f = this.getBrightness();
        BlockPos blockpos = this.getRidingEntity() instanceof EntityBoat ? (new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ)).up() : new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ);
		//亮度大于 0.5
        if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(blockpos))
        {
            boolean flag = true;
            ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
			//头顶没有物品
            if (!itemstack.isEmpty())
            {
                if (itemstack.isItemStackDamageable())
                {
                    itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));

                    if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
                    {
                        this.renderBrokenItemStack(itemstack);
                        this.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                    }
                }

                flag = false;
            }

            if (flag)
            {
                //燃烧 8s
                this.setFire(8);
            }
        }
    }

    super.onLivingUpdate();
}
```



### 3. 生物自然生成

![image-20230103142912742](mod%E5%BC%80%E5%8F%91.assets/image-20230103142912742.png)



* github.zimoyin.mymod.init.ModSpawn

```java
//addNormalSpawn 找到这个方法
//所有地形都可以生成
private static void addNormalSpawn(Map<Type, Set<Biome>> biomeMap) {
    for (Biome biome : Biome.REGISTRY) {
        //添加行代码
        //生物群系、（这里是配置文件里的）权重、(区块单位内)最少生成、(单位内)最多生成
       // add(biome, ModConfig.SPAWN_CONF.SPAWN_TAINTER, EntitySkeletonWarrior.class, 1, 4);
        add(biome, 200, EntitySkeletonWarrior.class, 1, 4);
    }
}

//下面两个不显示，因为没有生物群系的判断。并且和上面差不多
private static void addOpenGroundSpawn(Map<Type, Set<Biome>> biomeMap)
private static void addHumidSpawn(Map<Type, Set<Biome>> biomeMap) 
//地狱生成
private static void addNetherSPAWN(Map<Type, Set<Biome>> biomeMap) 
//所有地形都可以生成
private static Map<Type, Set<Biome>> buildBiomeListByType()
```



### 4. 微调AI

![image-20230103144015823](mod%E5%BC%80%E5%8F%91.assets/image-20230103144015823.png)



* 原版骷髅AI `net.minecraft.entity.monster.AbstractSkeleton`

```java
protected void initEntityAI()
{
		//权重1，游泳
        this.tasks.addTask(1, new EntityAISwimming(this));
        //权重1，躲避太阳
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
}
```



* github.zimoyin.mymod.entity.creatures.misc.EntitySkeletonWarrior

```java
@Override
protected void initEntityAI()
{
   super.initEntityAI();//继承原版AI
    //攻击村民
    this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityVillager.class, true));
}
```



### 5. 微调属性

![image-20230103144951104](mod%E5%BC%80%E5%8F%91.assets/image-20230103144951104.png)



* 传统

```java
    /**
     * 这里继承父类的属性
     */
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        //跟随距离
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        //速度
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        //空手攻击力
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        //防御力（盔甲）
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        //血量上限
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(9.0D);
    }
```

* IDF 需要继承 `github.zimoyin.mymod.entity.creatures.EntityModUnit`  然后才能调用 `setAttr`





### 6. 持有物品

![image-20230103160633223](mod%E5%BC%80%E5%8F%91.assets/image-20230103160633223.png)



* 骷髅武器逻辑 `net.minecraft.entity.monster.AbstractSkeleton`

```java
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        //根据难度随机出附魔
        this.setEquipmentBasedOnDifficulty(difficulty);
        //setEquipmentBasedOnDifficulty()内的代码   ：主手放弓
        // this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        //根据难度随机出附魔
        this.setEnchantmentBasedOnDifficulty(difficulty);
        //附魔代码
        //this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItemMainhand(), (int)(5.0F + f * (float)this.rand.nextInt(18)), false));

        //调整近战或者远程（骷髅可以手中物品来调整近战或者远程
        this.setCombatTask();
        //骷髅捡取东西的概率为0.55
        this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());

        //头部为空
        if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())
        {
            Calendar calendar = this.world.getCurrentDate();
            //万圣节期间
            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
            {
                //带上南瓜或者南瓜灯（概率
                this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
            }
        }
        return livingdata;
    }
```





* 添加代码 `github.zimoyin.mymod.entity.creatures.misc.EntitySkeletonWarrior`

```java
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        //this.getItemStackFromSlot
        //this.setItemStackToSlot
        
        //设置装备
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.TEST));
        this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.COMMAND_BLOCK_MINECART));
        this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Blocks.MOB_SPAWNER));
        this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));

        //设置附魔
        // 参数： 主手 (获取随机数,获取主手物品,10点经验等级的附魔,false 禁止宝藏(冰霜行者等)附魔)
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItemMainhand(), 10, false));
        this.setItemStackToSlot(EntityEquipmentSlot.CHEST, EnchantmentHelper.addRandomEnchantment(this.rand, this.getItemStackFromSlot(EntityEquipmentSlot.CHEST), 10, false));

        //第二种方式设置装备和附魔
//        ItemStack itemStack = new ItemStack(ModItems.TEST)
//        //itemStack.addEnchantment(EnchantmentHelper.addRandomEnchantment());//通过附魔工具类随机附魔
//        itemStack.addEnchantment(ModEnchantmentInit.ANTI_VANILLA,1);//指定附魔
//        this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, itemStack);

        //调整近战或者远程（骷髅可以手中物品来调整近战或者远程
//        this.setCombatTask();
        //骷髅捡取东西
        this.setCanPickUpLoot(true);
        return livingdata;
    }
```







### 7. 掉落物

在原版里面这个是靠战利品表来制作，但是这里就粗暴的制作一下了



* 添加代码 `github.zimoyin.mymod.entity.creatures.misc.EntitySkeletonWarrior`

```java
    /**
     * 掉落物
     * @param wasRecentlyHit 生物是否被打过
     * @param lootingModifier 玩家幸运值
     * @param source 伤害来源
     */
    @Override
    protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        //掉落自己的剑
        if (!world.isRemote) {
            dropItem(ModItems.TEST,1);
        }
    }
```



## 十四、新增简单buff

![image-20230106134655167](mod%E5%BC%80%E5%8F%91.assets/image-20230106134655167.png)

![image-20230106134806522](mod%E5%BC%80%E5%8F%91.assets/image-20230106134806522.png)

![image-20230106134845037](mod%E5%BC%80%E5%8F%91.assets/image-20230106134845037.png)



![image-20230106134906543](mod%E5%BC%80%E5%8F%91.assets/image-20230106134906543.png)



### 1. 新增Buff



* github.zimoyin.mymod.potion.ModPotions

添加buff

> 虽然状态效果的具体逻辑是需要通过其他手段（事件订阅、方块更新、物品使用等）来实现的，但有一小部分原版状态效果的逻辑是直接利用[生物实体属性](https://harbinger.covertdragon.team/chapter-08/attribute/modifier.html)实现的。 具体来说，这些状态效果的逻辑是通过在生物实体获得状态效果后，调用 `Potion.applyAttributesModifiersToEntity`（`func_111185_a`）来实现的。
>
> - 速度（`MobEffects.SPEED`），提升生物移动速度（`SharedMonsterAttributes.MOVEMENT_SPEED`）
> - 迟缓（`MobEffects.SLOWNESS`），降低生物移动速度
> - 急迫（`MobEffects.HASTE`），（忽略挖掘速度）提升攻击速度（`SharedMonsterAttributes.ATTACK_SPEED`）
> - 挖掘疲劳（`MobEffects.MINING_FATIGUE`），（忽略挖掘速度）降低攻击速度
> - 力量（`MobEffects.STRENGTH`），提升攻击力（`SharedMonsterAttributes.ATTACK_DAMAGE`）
> - 虚弱（`MobEffects.WEAKNESS`），降低攻击力
> - 生命提升（`MobEffects.HEALTH_BOOST`），提升生命值上限（`SharedMonsterAttributes.MAX_HEALTH`）
> - 幸运（`MobEffects.LUCK`），提升幸运值（`SharedMonsterAttributes.LUCK`）
> - 霉运（`MobEffects.UNLUCK`），降低幸运值
>
> 所有 可以参考这部分原理。但在IDF 对这些做了封装，如 new BasePotion(false, 0xcccc00, "my_buff", 0).setXXX();

```java
// BasePotion: IDF 的一个简单 Potion 可以通过方法对其设置效果，如果想定制请仿写
//参数: 是否负面效果,颜色,注册名称,图标序号（textures/misc/potions.png 拼图，从下开始依次向右开始序号,并该图片的下两行被原版占用）
public static final Potion MY_POTION = new BasePotion(false, 0xcccc00, "my_buff", 0);

```

注册buff

```java
@SubscribeEvent
public static void registerPotions(RegistryEvent.Register<Potion> evt)
{
    //VIRUS_ONE.tuples.add(new EffectTuple(0.2f, MobEffects.NAUSEA, 100));

    //注册buff
    INSTANCES.add(MY_POTION);
    
    evt.getRegistry().registerAll(INSTANCES.toArray(new Potion[0]));
    IdlFramework.LogWarning("registered %d potion", INSTANCES.size());
}
```

拓展：自动注册buff

```java
@SubscribeEvent
public static void registerPotions(RegistryEvent.Register<Potion> evt)
{
    //VIRUS_ONE.tuples.add(new EffectTuple(0.2f, MobEffects.NAUSEA, 100));

    for (Field field : ModPotions.class.getFields()) {
        if (Potion.class.isAssignableFrom( field.getType())) {
            try {
                INSTANCES.add((Potion) field.get(INSTANCES));
            } catch (IllegalAccessException e) {
                LoggerFactory.getLogger().error("反射添加buff失败",e);
            }
        }
    }
    //        INSTANCES.add(MY_POTION);
    evt.getRegistry().registerAll(INSTANCES.toArray(new Potion[0]));
    IdlFramework.LogWarning("registered %d potion", INSTANCES.size());
}
```

* github.zimoyin.mymod.potion.buff.BaseSimplePotion

修改遗留BUG

```java
//    protected static final ResourceLocation resource = new ResourceLocation("idlframewok","textures/misc/potions.png");
    protected static final ResourceLocation resource = new ResourceLocation(IdlFramework.MODID,"textures/misc/potions.png");
```

```java
public BaseSimplePotion(boolean isBadEffectIn, int liquidColorIn, String name, int icon) {
    super(isBadEffectIn, liquidColorIn);
    setRegistryName(new ResourceLocation(Reference.MOD_ID, name));

    //        setPotionName("idlframewok.potion." + name);
    setPotionName("potion." + name);

    iconIndex = icon;
}
```

* *.lang

```properties
potion.my_buff=我的第一个buff
```

* IDF 设置效果

![image-20230106142600493](mod%E5%BC%80%E5%8F%91.assets/image-20230106142600493.png)



* 关于资源图片

![image-20230106141631383](mod%E5%BC%80%E5%8F%91.assets/image-20230106141631383.png)





## 十五、物品的右键使用

![image-20230106145446306](mod%E5%BC%80%E5%8F%91.assets/image-20230106145446306.png)



* github.zimoyin.mymod.item.weapon.ItemTestSword

这里使用我们上次添加过的剑来实验

```java
@Override
public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!worldIn.isRemote) {
        player.heal(1f);//回血
        //被右键的方块设置为泥土
        worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
    }
    return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
}

@Override
public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
    if (!playerIn.world.isRemote) {
        playerIn.heal(1f);//回血
        target.attackEntityFrom(DamageSource.GENERIC,45f);//让对面掉血
        //如果对面正在着火则灭火,否则点火
        if (target.isBurning()) {
            target.extinguish();
        }else {
            target.setFire(6);
        }
        //设置耐久度
        //            stack.setItemDamage(1);
        //消耗耐久
        //player.getHeldItemMainhand().damageItem( 7,player);
        //消耗一个物品
        //            playerIn.getHeldItem(hand).shrink(1);
        //设置冷却 20tick
        //            playerIn.getCooldownTracker().setCooldown(stack.getItem(),20);
        //            playerIn.getCooldownTracker().setCooldown(playerIn.getHeldItem(hand).getItem(),20);
        //            ItemSkillBase.activateCoolDown(playerIn,playerIn.getHeldItem(hand));
    }
    return super.itemInteractionForEntity(stack, playerIn, target, hand);
}

@Override
public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    if (!worldIn.isRemote) {
        playerIn.heal(1f);//回血
    }
    return super.onItemRightClick(worldIn, playerIn, handIn);
}
```





## 十六、物品防爆

### 1. 思路:

* 监听伤害事件
* 原版思路
* 重写 ItemEntity



### 2. 原版思路

* net.minecraft.entity.item.EntityItem

> 无法修改该类所以不能在这里添加代码(此类无任何子类)，并且物品类没有继承此类因此无法修改
>
> 所以考虑是否可以继承他（思路3）

![image-20230106195009556](mod%E5%BC%80%E5%8F%91.assets/image-20230106195009556.png)





### 3. 伤害事件 EntityLivingBase

1. 掉落物称为物品实体，玩家也是实体，但是物品实体收到(爆炸)伤害无法除非此事件

2. 盔甲架，矿车称为准活物。受到伤害可以被某些事件监听到



### 4.重写 ItemEntity

![image-20230106195813003](mod%E5%BC%80%E5%8F%91.assets/image-20230106195813003.png)



* github.zimoyin.mymod.entity.EntityItemMyNoBoom

> 继承 EntityItem 写自己的实体

```java
public class EntityItemMyNoBoom extends EntityItem {
    public EntityItemMyNoBoom(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntityItemMyNoBoom(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
    }

    public EntityItemMyNoBoom(World worldIn) {
        super(worldIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        //如果受到了爆炸伤害则禁止执行
        if (source.isExplosion()) return false;
        return super.attackEntityFrom(source, amount);
    }
}

```





* net.minecraftforge.common.ForgeInternalHandler

> 如何确定加载的是自己写的实体而不是原版的呢？
>
> 原版代码：

```java
@SubscribeEvent(priority = EventPriority.HIGHEST)
public void onEntityJoinWorld(EntityJoinWorldEvent event)
{
    if (!event.getWorld().isRemote)
    {
        ForgeChunkManager.loadEntity(event.getEntity());
    }

    //先生成原版实体
    Entity entity = event.getEntity();
    if (entity.getClass().equals(EntityItem.class))
    {
        ItemStack stack = ((EntityItem)entity).getItem();
        Item item = stack.getItem();
        //mc优先通过物品的hasCustomEntity方法检测是否有自定义实体
        //有则使用自定义的实体
        if (item.hasCustomEntity(stack))
        {
            //调用实体
            Entity newEntity = item.createEntity(event.getWorld(), entity, stack);
            if (newEntity != null)
            {
                entity.setDead();
                event.setCanceled(true);
                event.getWorld().spawnEntity(newEntity);
            }
        }
    }
}
```



* github.zimoyin.mymod.item.ItemMyNoBoom

> 重写自己的物品的掉落物的逻辑

```java
public class ItemMyNoBoom extends ItemBase {
    public ItemMyNoBoom(String name) {
        super(name);
    }

    /**
     * 是否有自定义实体
     *
     * @param stack The current item stack
     * @return true 拥有自己的自定义实体，将优先加载自己的实体
     */
    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    /**
     * 创建实体
     *
     * @param world     The world object
     * @param location  The EntityItem object, useful for getting the position of the entity
     * @param itemstack The current item stack
     * @return 自己的实体
     */
    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        //创建自己的实体
        EntityItem entityItem = new EntityItemMyNoBoom(world, location.posX, location.posY, location.posZ,itemstack);

        entityItem.motionX = location.motionX;
        entityItem.motionY = location.motionY;
        entityItem.motionZ = location.motionZ;

        //设置拾取时间 40tk 否则刚扔出去就被拾取回来了
        //但是拾取时间应该与原版一致。因为在不同情况下 拾取时间是不同的，比如怪物掉落的物品，和你扔出的物品
//        entityItem.setPickupDelay(40);
        //由于原版 的pickupDelay是私有的所以无法调用，但是我们可以通过其他手段读取
        //private int pickupDelay;
//        entityItem.setPickupDelay(location.pickupDelay);

        //匠魂代码
        //通过NBTTagCompound写入数据，然后再读取数据
        NBTTagCompound tagCompound = new NBTTagCompound();//存储mc各种数据的地方
        location.writeToNBT(tagCompound);//数据写入到nbt
        short pickupDelay = tagCompound.getShort("PickupDelay");//从nbt获取到

        //直接从实体的NBTTagCompound读取数据
        //代码未能经过实战检验，不确定是否有问题
//        short pickupDelay = entityItem.getEntityData().getShort("PickupDelay");

        entityItem.setPickupDelay(pickupDelay);
        return entityItem;
    }
}

```



* github.zimoyin.mymod.item.ModItems

> 注册物品

```java
public static final Item TEST_BOOM = new ItemMyNoBoom("test_boom");
```



## 十七、命令注册

### 1. 简单框架搭建

* github.zimoyin.mymod.IdlFramework

```java
//注册命令
@EventHandler
public static void onServerStarting(FMLServerStartingEvent event) {
    //客户端只需要能发送聊天消息就能使用这些服务器上有的命令
    LoggerFactory.debug("加载服务器端命令");
    for (CommandBase command : ModCommands.COMMANDS) {
        event.registerServerCommand(command);   
    }
    // 客户端专有的命令可以考虑走这个.这样一来可以添加一些只在客户端上才有意义的命令（比如渲染相关的命令——服务器上渲染啥？）。
     //LoggerFactory.debug("加载客户端端命令");
	//ClientCommandHandler.instance.registerCommand(new MyCommand());
}
```

* github.zimoyin.mymod.command.ModCommands

```java
public class ModCommands {
    public static List<CommandBase> COMMANDS = new ArrayList<CommandBase>();

    static {
        //注册命令
        COMMANDS.addAll(Arrays.asList(
                new CommandAddKillCount(),
                new CommandAddKillCount2(),
        ));
    }
}
```



## 十八、声音

### IDF

* github.zimoyin.addkill.util.ModSoundHandler

```java
public static SoundEvent SOUND_2 = new ModSoundEvent("entity.moroon.hurt");  //IDF 搞定了注册
```

### 原版

* 创建声音对象

Minecraft 中有各种各样的音效——某个维度的背景音乐、某个实体游荡时的声音、方块破坏的声音、等等。每一个这样的音效都对应着一个 `SoundEvent` 对象。

```java
import net.minecraft.util.SoundEvent;

public static final SoundEvent A_NEW_SOUND = new SoundEvent(new ResourceLocation("my_mod", "a_new_sound"));
```

* 注册他们

这些 `SoundEvent` 也是需要注册的。原版使用的 `SoundEvent` 对象都可以在 `net.minecraft.init.SoundEvents` 这个类中找到。

```java
// 注意到注册名和构造时传入的那个“音效名”不是一回事。
// 通常，为简单起见，一般会在这两个地方使用同样的名字。
@SubscribeEvent
public static void onSoundEvenrRegistration(RegistryEvent.Register<SoundEvent> event) {
    event.getRegistry().register(A_NEW_SOUND.setRegistryName(new ResourceLocation("my_mod", "a_new_sound")));
}
```



### sounds.json

但是 `SoundEvent` 这个对象的构造器只接受一个 `ResourceLocation` 作为参数。那它为什么能代表一个具体的声音文件？因为具体的音效定义是在 [`assets/[mod_id\]/sounds.json`](https://minecraft-zh.gamepedia.com/Sounds.json) 中出现的。

```json
{
    "a_new_sound": {
        "category": "block",
        "subtitle": "my_mod.sound.a_new_sound",
        "sounds": [
            "my_mod:music/one_new_sound",
            "my_mod:music/another_new_sound"
        ]
    }
}
```

具体来说，名为 `a_new_sound` 的 JSON Object 指向了之前创建的 `A_NEW_SOUND` 对象，可以注意到这个名字和构造器中传入的 `ResourceLocation` 的 `path` 部分是一样的。 然后看一下这个 JSON Object 中的各个字段：

- `category` 代表了这个音效的“类别”。

- `subtitle` 是指开始播放声音时右下角弹出的文字说明的本地化键。

- `sounds` 字段定义了具体的音效，注意到这是一个 JSON 数组，它的含义是“在需要使用音效时，随机从指定数组中选一个音频并使用”。该字段只能为包含 JSON String 的数组，其中：

  - `my_mod:sound/one_new_sound` 代表 `assets/my_mod/sounds/music/one_new_sound.ogg` 这个文件。
  - `my_mod:music/another_new_sound` 则代表 `assets/my_mod/sounds/music/another_new_sound.ogg` 这个文件。
  - `my_mod:` 后面的路径映射`assets/addkill/sounds`以为根的路径

是的，Minecraft 使用的声音文件统一为 `ogg` 格式。Minecraft 并不支持其他格式的音频文件，所以只能先把其他格式文件转码为 `ogg` 格式才能在 Minecraft 中使用。（当然你也可以考虑塞个解码器进去，但那样无异于绕开绝大部分 Minecraft 的音频管理机制。）



### 播放

* World.playSound

这个方法有三个版本：

| Serage          | 签名/参数列表                                                | 解释                                                         |
| :-------------- | :----------------------------------------------------------- | :----------------------------------------------------------- |
| `func_184133_a` | `void playSound(EntityPlayer player, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch)` | `func_184148_a` 的三个 `double` 换成 `BlockPos` 的版本。     |
| `func_184134_a` | `void playSound(double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean distanceDelay)` | 不需要玩家实体的版本。只在物理客户端上有用。                 |
| `func_184148_a` | `void playSound(EntityPlayer player, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch)` | 只在逻辑服务器上有用。也因此可以被服务器端插件等利用。表面上它需要一个玩家实体，但实际上你可以传入 `null`。 |

用这个方法可以播放任意音效。



## 十九、粒子效果

创建粒子的方法 ： 可以看到这六个实现分别来自两个地方，一个是抽象类` World` ，一个是实现类 `WorldServer` 。来自实现类中的 spawnParticle 方法并没有覆盖重写 父类中的 spawnParticle ，仅仅是添加三个重载方法

![image-20230201185636395](mod%E5%BC%80%E5%8F%91.assets/image-20230201185636395.png)

* 一下代码给出了示例
* 当在服务端调用 `WorldServer` 下的方法可以生成粒子
* 当再客户端调用 父类World 下的方法无法生成粒子，注意 IDF框架内 `github.zimoyin.addkill.util.EntityUtil.SpawnParticleAround()` 方法是针对于客户端的
* 总结：当在客户端端调用生成粒子方法时请调用，`World` 下的方法。或者调用IDF提供的工具类
* 总结：当在服务端调用生成粒子方法时请调用，`WorldServer` 下的方法。

```java
    /**
     * 代码中的 LivingHurtEvent 被监听并处理，当玩家受到伤害时在玩家周围生成 CRIT 粒子，该粒子将在客户端呈现。
     *
     * @param event
     */
    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
            WorldServer world = player.getServerWorld();

            IdlFramework.LogWarning("世界是否是客户端: " + world.isRemote);
            for (int i = 0; i < 1000; i++) {
                //net.minecraft.world.WorldServer.spawnParticle
                world.spawnParticle(EnumParticleTypes.END_ROD,//粒子类型
                        player.posX,//粒子初始位置
                        player.posY,
                        player.posZ,
                        50,//粒子数
                        0.5,//偏移位置：最终位置
                        0.5,
                        0.5,
                        0.0);//偏移速度
/*                world.spawnParticle(EnumParticleTypes.END_ROD,
                        true,
                        player.posX,
                        player.posY,
                        player.posZ,
                        50,
                        0.5,
                        0.5,
                        0.5,
                        0.0);*/
                //net.minecraft.world.World.spawnParticle
/*                world.spawnParticle(EnumParticleTypes.END_ROD,
                        player.posX ,
                        player.posY ,
                        player.posZ ,
                        0,
                        0,
                        0);*/
            }
        }
    }
```





## END

/locate fortress  定位下界堡垒

###  [1. Access Transformers](https://docs.minecraftforge.net/en/latest/advanced/accesstransformers/#access-transformers)

Access Transformers (ATs for short) allow for widening the visibility and modifying the `final` flags of classes, methods, and fields. They allow modders to access and modify otherwise inaccessible members in classes outside their control.

### [2. Mixin](https://blog.csdn.net/smildwind/article/details/120227376)

允许MOD修改原版的字节码并提供了一套成熟与便捷的修改工具

### [3. 第三方 Capability 注入](https://harbinger.covertdragon.team/chapter-29/foreign-capabilities.html)

### 4. 依赖mod

实例：https://github.com/WJXhhh/KaBlade

已知导包方式

```java
	1. compileOnly files("libs/SlashBlade-mc1.12-r33.jar")
    2. compileOnly(files("./libs/SlashBlade-dev3.jar"))
    3. runtimeOnly(files("./libs/SlashBladeRuntime.jar"))
```

2 和 3 配套的

```java
buildscript {
    repositories {
        mavenCentral()
        maven { url = 'https://gitlab.com/api/v4/projects/26758973/packages/maven' }
        maven { url = 'https://maven.minecraftforge.net/' }
    }
    dependencies {
        classpath group: 'net.minecraftforge.gradle', name: 'ForgeGradle', version: '5.+'
        classpath group: 'wtf.gofancy.fancygradle', name: 'wtf.gofancy.fancygradle.gradle.plugin',    version: '+'
        // MixinGradle:
        classpath 'org.spongepowered:mixingradle:0.7.+'
    }
}
```



## 日志

* mc 使用log4j2 框架进行日志管理，建议对以下对面进行封装

```java
LogManager.getLogger(IdlFramework.MODID+":"+this.getClass().name());
```

* 封装

```java
public class LoggerFactory {
    public static Logger getLogger(){
        return IdlFramework.logger;
    }

    public static Logger getLogger(String name){
        return createLogger(name);
    }

    public static Logger getLogger(Class<?> cla){
        return createLogger(cla.getName());
    }

    public static Logger getLogger(Object obj){
        return createLogger(obj.getClass().getName());
    }
    public static void debug(String message){
        getLogger().info(message);
    }

    public static void info(String message){
        getLogger().info(message);
    }
    public static void warn(String message){
        getLogger().warn(message);
    }
    public static void error(String message){
        getLogger().error(message);
    }

    private static Logger createLogger(String name){
        return LogManager.getLogger(IdlFramework.MODID+"/"+name);
    }
}
```

* IDF提供了日志实例但是不推荐

```java
IdlFramework.logger
```

