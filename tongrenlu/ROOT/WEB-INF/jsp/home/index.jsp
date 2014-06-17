<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>

<html>
<head>
<title><fmt:message key="app.name"/> http://www.tongrenlu.info</title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_meta.jsp" %>
<meta name="description" content="东方Project 同人资源分享">
<meta name="keywords" content="东方Project,同人音乐,同人志">
<meta property="wb:webmaster" content="8e90072a3b17c980" />
</head>
<body>
<div class="text-center sr-only">
<pre style="background: none; border: none; line-height: 8px; font-size: 8px; ">
                                                                                  :7u                                   
                                                                            .:i7kLi ,B                                  
                                                                        :u0OBFiMN    Bi                                 
                                                                    :1MBMq27: YL8u   LE                                 
                                                                 iPBBNjL7Lj: kr7vB.71B8                                 
                                                              :SBBPL77L7LvU,.,,v2BB:BB:                                 
                                                            7BB0L77LvL7YvYYU7r, ,MG G            .:,  ..                
                                                          UBBU77LvLvLvLLLvJ;:qU .qB Z           .rii::;;r               
                                                        FB8YrLLL7LLLvYLL7L7jk: uZFB:uB7         .r:::::ii               
                                                      XBGv77LvLvYLLvL7L777ju ,Bki2B51BB.         :r:::;i.               
                                                    YBZLrLvLvLvYvL7L777LJ1Sk:.:SS2Bi  Pr          .iii.                 
                                                  .BBv77L7L7L7v77r7rLY22F21SqFi 72Br  B:      ..                        
                                               . LB2:rrr;7r7r77LYUu2U2uUjuJ5:JZ. 2B1BBB      ,,                         
                            ...             :qBq2qNPZ1U2XkENGGM8M8MBBBBBBOOEB2.:2GOrU:      ..                          
                .;YFPOOBBBMBBBMMMMOONqUvNBMFku7JUNZMBM0Nkk21uuYJLYJF2FU2U22SkqZBMBjE.                                   
            i2MBBMZk5uuYYLL7L7vvLLJYUuF1uSBZ2N8E05UYY7YLJYjYjYuJjJjLYLYLJYJLYLJLjY50GU7.                                
         7OBM0jL777L7L7LvYvL7L7LvLvLvL;ruGGMS5YYLYYJYUYUjUJUJuJujUJuYuJuJUJuYUJuYJLJuq0BMPi                             
      ,UBBUiL7L7LvLvLLLvLvYvLvLvYvL77rSEEUJ7YYuJujuJUJUjUjUjUjujuJujUJjYuJuJuJUJujuJUS0F2FMBZJ:                         
   7UrNO.,:vUYvLvLvLvYvLvLvYLLvL7L7r2MqULYYuJUJuJUjujuJUjUjujujUJUJUjujuLuYujUjUYujuYLJPGO2jUZBBUi                      
  ;B  :MLi:,:YYvYvLvYLLvYvYLYvY7v;LZOYLJjJuJuJUJuJUJUjUjujUJuYjYuJuJUYjGS7YJUJuJULJYkXJrL5MBM2UUqMB82i                  
   kUi,Bv,5i UYYvLLLvYvYvYvLvL77iSB575k1YUJujuJUJjYUjUjUjUJjLuYUvjJujuL1BNYLYj12FZLLLNMPY7r2MBM8EE0MBBBBBGYri,..        
    NM::BLY U1i7YLYvLLY7L7L7v777OMLYBZUYuJuJUjuJjYXJujUjuYYLq21MF7jJuJj7MBMFYvuZUGBSYr1BBXur72BBBBBBBBBBBBBBMu:         
      7:vB. i77UJYvL7v7v777YJUYBO7qBSYYUJUJujUJJL8PYJUJuYLjGB1PMMXvJJUJLUBBB0jru8FBBGSrUMBMqLrYBu                       
       G MMii,..rLL7LLJJUUF55YGBYMBYLYUJUJUJUYJ7NBuLUJjLYF8BBPG8GMN7YJjLjBM1BO2;S8NBG8Z7FG88M1rJBv                      
       BB.5BY:uL 2kX5S1S1F22YSBqMBLYYUJjJUjUJuvSBMvjYuLJNM8B0Z0BNGOqvJYJYMB 5BM57ZqBM0G81EEZZBqv5BM                     
       0F  MBUGr.BUrZXP21U1uYBMOBLLJuJjYSjuJuLUOBqLYuLYEMZBBNNEBMNGON7jYY8B, BBBNNM8B8N0GNZNEMBNYPBO                    
        7Z, 2Bq :ur;77YX55UJFBGBLLYUJjYq1JYjYYNMBS7jYYEMZM7BqEGGB80OMk7YLMBi  BBEP0ZBBBO0E0ZqMBZE1ZBj                   
         .NJ PBr::iiii iES1LMMBYLYUJjY8PLYkYYUMOB2LLLqM0BU.BMMB:uBG0OM27uOBr   BB0ZqB,ZBZNEqENBMM00OB.                  
           rBE1BBqq7FB YUrF2BBUL0JJuvqBjvFXYv0ZBB17LFMEMB77B0BB  BBZ0OZvUMBY   :B8qNB. BB0NEN0BBMBG8BB                  
              qBMBGUPY OS7JYBq7BkLuvUBZvYq07YGEMBk7YBBBBB rB8B7   BBZ0MSq8BY    SBG8B  ,BBEqZPB1 ZBBMB:                 
              Bk ,0BG. i,, YMLNBJYYY0Bk7v8N7UMqBO0LMBMNB5 JBBM    :BMG0OZ8B7   7BBBBBB. uBBEN0B02: BBBM                 
              .1Y  .MXUj1iPBuNMBYLL1MB27J88rqG0MOZB8ENGB7 UBB      LBEZ0GMB.  iBBBB87BB; E8BEqBB1B  8BB                 
                i1:vGi;5q0ZXGBBBvYLOZBjvYMGL08XBMM0ENENB. iB:.,.    8B0ZEBG   BBBBBB. BB.F2NBEBS X   UBi                
                 FBBBB07BBMMBOOBL75OEB27JGMuGNZBBPENEP0BrvBBMBBBM;   BBEBB.   vBBBBB0 :BS:M uBB0,U7   1N                
                ,B8ZEMMBEG7EBNNBY7qMqBk7LMONNEMMB0NZPEBBBBBBB5vOBBu  YBMB7     BB8BBB  Bk BU;BBOUBE    ,                
                BBZNGBBr JkXBEkMu7OZ0BBrL0MEE0BMBO0XGBBBBBBBBM. .rY.  BB5         B8B  ZJ PBJuBEj5Br                    
               BBG0E00EBEBGLBMYM5j8ZEBBjrN8GqMBr,BqGBBBBBBBBBBB  :,   BP        1BBFB. :.  BBYOZ5S7GL                   
              BB80ZNZ0ZqBBYLBB7qGuOqB0BMr2M0EBUL:BBBBBBBBBBBBBBL      :         .BqSB,      BG7YP.  M,rj                
            .BBBZZ0ZNEN8BuLY8Br1BF0BBMBB2L880BF0.LBBBEBBF0BBMJBB                 .BBqi      .M ...PqS .q                
           5BBBN0EENZ00BGr1YPB7vBkEB1qJ BLPGGBJ: ;BBB;    BBBruB                       .:.,  BM2JU0P8BBE:..             
         8BBBMGSZZNZ0E8B7jUUUBYrEBZBY iP0GU8EB: rUkBBB FSY0M1r0B.                     .,.    BBMBP,BBZZBBBGPY7,.        
      .;EML. XXEZNMNEqBk;22juBU7UBBMBXMX.7OqGBkJX1UM,1P,BBNkZBBEj                           NBBOGBBOZNZNMBBGEYr7L77i:   
             Bq8NMMNqMBiLFUUYBjkuZBMNMMB1.iBOBPUU1J0Z .  7SSvi                  :          1BGBMqZqkG8NZqMM  :......    
            jBOEMBBP0BS:111uuBLPELBGZX8GBGYYMBML1U1JMB. .                  .:iJEBB        ZBOMBEMG8Y2O8NE0B7.i.         
            NBOBOrBMMBi711UUUBrEBrZBGj5MEBS7:XB0Y225UMB:   . ..       iYLUuuY7:.vB      :BBMGB:qBBOkrS8Z0EB2.r.         
            GBBu. JBBSivSU2YPMuMBUrFBU7ZMEBki:YZFLXPP2:BL            rM::::,,,:.rF    .OB7BBB7 BB2BPv70GGZBr,;          
            5Bu ,..GBv;J11uJEGBBSB7780rUMGZB2irLuuu7r7 LBYri         F5.:::i:i:,.   iMB2 :BBi 7B: ZBvL2MZBZ.i,          
            iB,:   rBr7UX2FJBBEi1BMrUZY7qOZGBJrirY,:r, MB..B          Xi,,,.     rZBM7,.,;L..:ki:  BN71BBO,::           
             Zr.   rB7vYqPiZBBv  vB0rS2LYMZ0MB.... v1BM7LBBZ,               .ijGGP7:.,:::i:i::i,   .BJMB2,:.            
             ,:    JO:.,.i5BrJ7LL .BP72YLkMG8BBF7r7E1,rGBMBBBBBv. .:irLJFF11UJ7:,.::iir;;:.  ..     0BMi,.              
                   Mj :YY:k .GZMBBLqBF7uLuGMBj  BBBB, vB80NMMBBBBBB2j1Yv;i:::::iirir::..           .kJ                  
                   B:v77BBNJBMPiPi..vBXvj70ZBBLrBBOBBBBZZNZqEEBBENBB..,:::iirii:,.                                      
                  .BXZBqSvOU    LBU7ijBXvL1MMBBBqYPU5OOMEZ0G0EPMG. r:::iii::.                                           
                  BU  Brr2B8ivUjP0:.: 7Bk7UMB:BMBLJS771qMMMZOGGqBB, ,::                                                 
                  5U7PB.  ,:::.        :B2LBB iBBBPMMU;7L1XNPG8MEBBJ.,:i.                                               
                                        :BUB0  iBBMNBBBSULLLJLjPBMBBB5:iL;                                              
                                         iBBi,:,.GB;iEBBMMBMZEkEBBM01rii:.                                              
                                          BB:. .:.JG: ,i: .i7LLri:. .                                                   
                                          2v       .:::::.                                                              
                                          .              ..                                                             
                                                                                                                        
</pre>
</div>
<%@ include file="/WEB-INF/jsp/inc/home_header.jsp" %>

<div id="section-music" class="section">
    <div class="container">
        <div class="row">
            <div class="col-sm-6 col-md-8 append-bottom">
                <h2 class="" style="margin:0;">
                    <a href="<c:url value="/music"/>" title="<fmt:message key="app.music"/>">
                        <fmt:message key="app.music"/> &gt;&gt;
                    </a>
                </h2>
            </div>
            <div class="col-sm-6 col-md-4 append-bottom">
                <form id="search-form" action="<c:url value="/search"/>" style="margin: 0;" class="form-search">
                    <div class="input-group">
                        <input type="text" class="form-control" name="q" value="<c:out value="${searchQuery}"/>">
                        <span class="input-group-btn">
                            <button  type="submit" class="btn btn-warning" type="button">
                                <span class="glyphicon glyphicon-search"> 搜索</span>
                            </button>
                        </span>
                    </div><!-- /input-group -->
                </form>
            </div>
        </div>
        <div id="gallery" class="gallery media-list clearfix">
            <c:forEach items="${lastCommentMusic}" var="articleBean" begin="0" end="29" varStatus="stat">
                <c:choose>
                    <c:when test="${stat.index eq 0}">
                        <div class="media large pull-left ">
                            <a href='<c:url value="/music/${articleBean.articleId}"/>' 
                                title="<c:out value="${articleBean.title}"/>"
                                class="">
                                <img src="<%= FILE_PATH %>/${articleBean.articleId}/cover_400.jpg"
                                    alt="<c:out value="${articleBean.title}"/>">
                            </a>
                            <a href='<c:url value="/music/${articleBean.articleId}"/>' 
                                title="<c:out value="${articleBean.title}"/>"
                                class="overlay">
                                <c:out value="${articleBean.title}"/>
                            </a>
                        </div>
                    </c:when>
                    <c:when test="${stat.index eq 22}">
                        <div class="media large pull-right">
                            <a href='<c:url value="/music/${articleBean.articleId}"/>' 
                                title="<c:out value="${articleBean.title}"/>"
                                class="">
                                <img src="<%= FILE_PATH %>/${articleBean.articleId}/cover_400.jpg"
                                    alt="<c:out value="${articleBean.title}"/>">
                            </a>
                            <a href='<c:url value="/music/${articleBean.articleId}"/>' 
                                title="<c:out value="${articleBean.title}"/>"
                                class="overlay">
                                <c:out value="${articleBean.title}"/>
                            </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="media pull-left">
                            <a href='<c:url value="/music/${articleBean.articleId}"/>' 
                                title="<c:out value="${articleBean.title}"/>"
                                class="">
                                <img src="<%= FILE_PATH %>/${articleBean.articleId}/cover_180.jpg"
                                    alt="<c:out value="${articleBean.title}"/>">
                            </a>
                            <a href='<c:url value="/music/${articleBean.articleId}"/>' 
                                title="<c:out value="${articleBean.title}"/>"
                                class="overlay">
                                <c:out value="${articleBean.title}"/>
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>
</div>
<div id="section-music-2" class="section">
    <div class="container">
        <%@ include file="/WEB-INF/jsp/inc/home_category_music.jsp" %>
    </div>
</div>
<div id="section-comic" class="section">
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-lg-8">
                <div class="page-header">
                    <h2>
                        <a href="<c:url value="/comic"/>" title="<fmt:message key="app.comic"/>">
                            <fmt:message key="app.comic"/> &gt;&gt;
                        </a>
                    </h2>
                </div>
                <div class="row cover-grid">
                    <c:forEach items="${lastCommentComic}" var="articleBean">
                        <div class="col-sm-6 col-md-3 col-lg-3 cover-object <c:if test="${articleBean.redFlg eq '1'}">red</c:if>">
                            <a href='<c:url value="/comic/${articleBean.articleId}"/>' 
                                title="<c:out value="${articleBean.title}"/>"
                                class="thumbnail">
                                <img src="<%= FILE_PATH %>/${articleBean.articleId}/cover_180.jpg"
                                    alt="<c:out value="${articleBean.title}"/>"
                                    class="">
                            </a>
                            <p class="">
                                <a href="<c:url value="/comic/${articleBean.articleId}"/>"
                                    title="<c:out value="${articleBean.title}"/>"
                                    class="">
                                    <c:out value="${articleBean.title}"/>
                                </a>
                            </p>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="col-md-4 col-lg-4 ">
                    <iframe width="100%" height="780" class="share_self" frameborder="0" scrolling="no" 
                        src="http://widget.weibo.com/weiboshow/index.php?language=&width=0&height=780&fansRow=2&ptype=1&speed=0&skin=1&isTitle=0&noborder=1&isWeibo=1&isFans=0&uid=2803325782&verifier=6daf321a&dpc=1">
                    </iframe>
            </div>
        </div>
    </div>
</div>
<div id="section-links" class="">
    <div class="container">
        <div class="page-header">
            <h2>有用的链接</h2>
        </div>
        <div class="row " >
            <div class="col-md-4 col-lg-4 ">
                <div class="media">
                    <div class="media-body">
                        <h3 class="">
                            <a href="http://down.thwiki.cc/" target="_blank" title="THBWiki下载站">THBWiki下载站</a>
                        </h3>
                        <p class="text-muted">音乐资源花样下载</p>
                    </div>
                </div>
            </div>
            <div class=" col-md-4 col-lg-4 ">
                <div class="media">
                    <div class="media-body">
                        <h3 class="">
                            <a href="http://bbs.nyasama.com/" target="_blank" title="喵玉殿论坛">喵玉殿论坛</a>
                        </h3>
                        <p class="text-muted">东方Project主题论坛</p>
                    </div>
                    <div class="row">
                        <div class="col-md-6 col-lg-6">
                            <a href="http://bbs.nyasama.com/forum.php?mod=forumdisplay&fid=93" target="_blank" title="喵玉殿论坛-同人资源板" class="btn btn-link btn-xs">
                                <span>同人资源板</span>
                            </a>
                        </div>
                        <div class="col-md-6 col-lg-6">
                            <a href="http://bbs.nyasama.com/forum.php?mod=forumdisplay&fid=3" target="_blank" title="喵玉殿论坛-喵玉汉化馆" class="btn btn-link btn-xs">
                                <span>喵玉汉化馆</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class=" col-md-4 col-lg-4 ">
                <div class="media">
                    <div class="media-body">
                        <h3 class="">
                            <a href="http://thvideo.tv/" target="_blank" title="TouhouVideo弹幕视频网">TouhouVideo弹幕视频网</a>
                        </h3>
                        <p class="text-muted">东方Project专门弹幕视频网站</p>
                    </div>
                </div>
            </div>
            <div class=" col-md-4 col-lg-4 ">
                <div class="media">
                    <div class="media-body">
                        <h3 class="">
                            <a href="http://tieba.baidu.com/f?kw=%B6%AB%B7%BD" target="_blank" title="东方吧">东方吧</a>
                        </h3>
                        <p class="text-muted">不解释</p>
                    </div>
                </div>
            </div>
            <div class=" col-md-4 col-lg-4 ">
                <div class="media">
                    <div class="media-body">
                        <h3 class="">
                            <a href="http://mengmao.org/" target="_blank" title="萌猫导航">萌猫导航</a>
                        </h3>
                        <p class="text-muted">喵～</p>
                    </div>
                </div>
            </div>
            <div class=" col-md-4 col-lg-4 ">
                <div class="media">
                    <div class="media-body">
                        <h3 class="">
                            <a href="http://app.tongrenlu.info/" target="_blank" title="APP下载">APP下载</a>
                        </h3>
                        <p class="text-muted">东方同人录安卓音乐客户端</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_login.jsp" %>
</body>
</html>