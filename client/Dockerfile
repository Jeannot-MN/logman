FROM nginx:1.21.1
ENV TZ="Africa/Johannesburg"
RUN rm -rf /usr/share/nginx/html/*
COPY nginx-default.conf /etc/nginx/conf.d/default.conf
COPY build /usr/share/nginx/html/
CMD ["nginx", "-g", "daemon off;"]

