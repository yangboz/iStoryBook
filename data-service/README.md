## Overview

SpringBoot(RESTful) + SwaggerUI + H2/MySQL + Memcached

### APIs

#### StoryBook

```
curl -i -X POST -H "Content-Type:application/json" -d "{\"name\" : \"Book 1\"}" http://localhost:8080/storybook/book 

```

#### StoryPage

```
curl -i -X POST -H "Content-Type:application/json" -d "{\"name\" : \"Publisher 1\"}" http://localhost:8080/storybook/page 
```

#### StoryPageView(Embed)

```

```

#### StoryBook_Page(Link the book with the page)

```
curl -i -X POST -H "Content-Type:application/json" -d  "{\"storyBook\" : \"http://localhost:8080/storybook/book/1\", \"storyPage\" : \"http://localhost:8080/storybook/page/1\"}"  http://localhost:8080/storybook/bookPage
```

