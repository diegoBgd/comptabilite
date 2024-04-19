 package utils;
 
 import java.io.IOException;
 import java.io.Serializable;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.log4j.Logger;
 
 
 
 public class TimeoutFilter
   implements Filter, Serializable
 {
   private static final long serialVersionUID = -6831936333548316793L;
   private static final Logger log = Logger.getLogger(TimeoutFilter.class);
  
   
   public void init(FilterConfig filterconfig) throws ServletException {}
 
  
   
   public void destroy() {}
 
 
 
   
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
     HttpServletRequest r = (HttpServletRequest)request;
     if (r.getSession(false) == null) {
       
       r.getSession();
       ((HttpServletResponse)response).sendRedirect(String.valueOf(String.valueOf(r.getContextPath())) + "/login.jsf");
     } else {
       
       chain.doFilter(request, response);
     } 
   }
 }


