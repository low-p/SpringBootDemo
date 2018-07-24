#git文件的相关操作    
$ cd [d]:/cd ../mkdir [file]   
   
#设置SSH KEY     
1、执行以下命令，创建SSH KEY, 在C盘用户目录下的.ssh目录，生成id_rsa和id_rsa.pub这两个文件([email]--邮箱,一般为Github注册邮箱)      
$ ssh-keygen -t rsa -C [email]     
2、登录Github, 进入Settings，找到SSH and GPG keys, 然后New SSH key，Title任意命名，   
   把步骤4中id_rsa.pub文件中的代码复制到Key下的文本框中，最后Add SSH key完成。
      
#设置用户名以及用户邮箱([email]--邮箱; [name]--用户名)       
//全局设置   
$ git config --global user.email [email]    
$ git config --global user.name [name]     
//进入config，当前项目设置    
$ git config user.email [email]    
$ git config user.name [name]
      
#分支相关操作    
//查看所有分支    
$ git branch -a/-r   
//切换本地分支   
$ git checkout [BranchName]      
//创建本地分支    
$ git branch [BranchName]   
//从远程仓库拉取特定分支(带有--track参数，所以要求git1.6.4以上)   
$ git checkout --track origin/[BranchName]   
//拉取远程分支并创建本地分支   
$ git branch checkout -b [LocalBranchName] origin/[remoteBranchName]   
$ git fetch origin [remoteBranchName]:[LocalBranchName]    
//提交本地分支数据到远程仓库   
$ git push origin [LocalBranchName]:[remoteBranchName]   
//合并某分支到当前分支    
$ git merge [BranchName]   
//设置git-push、pull的默认分支   
$ git branch --set-upstream-to=origin/[BranchName]   
$ git branch --unset-upstream master   
//删除本地仓库分支   
$ git branch -D [BranchName]   
//删除远程仓库分支   
$ git push origin --delete [BranchName]   
$ git push origin :[BranchName]   
      
#上传代码到Github步骤      
1、查看当前文件状态       
$ git status    
2、添加文件到暂存区([file]--文件; [dir]--目录; .--所有文件)      
$ git add [file]/[dir]/.   
3、提交暂存区文件到仓库区(-a--自上次commit之后的变化; [message]--提交说明)    
$ git commit [file]/-a -m [message]      
4、关联远程仓库【第一次上传时使用,之后忽略】([remote]--远程仓库)      
$ git remote add origin [remote]     
5、本地仓库提交到远程仓库(第一次提交时: $ git push -u origin master)         
$ git push origin master   
6、步骤4、5可能会弹出Github的登陆窗口，账号密码登陆即可。      
       
#更新代码提交到Github步骤       
1、查看有变更的文件       
$ git status    
2、更新文件到暂存区([file]--文件; [dir]--目录; .--所有文件)      
$ git add [file]/[dir]/.     
3、提交暂存区文件到仓库区([message]--提交说明)    
$ git commit [file]/-a -m [message]      
4、拉取远程仓库的变化，并与本地分支合并([remote]--远程仓库; [branch]--本地分支，可省略)       
$ git pull [remote] [branch]     
5、上传本地指定分支到远程仓库([remote]--远程仓库; [branch]--本地分支，可省略)        
$ git push [remote] [branch]       
