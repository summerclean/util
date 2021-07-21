package work.sihai.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Larry on 2018/8/24 0024.
 */
@Slf4j
public class RepeatFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 防止流读取一次后就没有了, 所以需要将流继续写出去，提供后续使用
        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
    }
}
