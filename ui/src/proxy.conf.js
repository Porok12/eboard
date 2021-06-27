const PROXY_CONFIG = [{
  "/api/*": {
    "target": "http://localhost:9000", // settings.environment.apiURL
    "secure": false,
    "logLevel": "debug",
    "changeOrigin": true,
    "bypass": function (req) {
      if (req && req.headers && req.headers.accept && req.headers.accept.indexOf("html")) {
        console.log("Skipping proxy for browser request");
        return "/index.html";
      }
    }
  }
}]

module.exports = PROXY_CONFIG;
