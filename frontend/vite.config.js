import { defineConfig } from "vite";
import scalaJSPlugin from "@scala-js/vite-plugin-scalajs";

export default defineConfig({
    plugins: [scalaJSPlugin()],
    server: {
        host: "127.0.0.1",
        strictPort: true,
        port: "5173"
    }
});