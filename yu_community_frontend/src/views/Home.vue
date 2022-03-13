<template>
  <div>
    <div class="box">🔔 {{ billboard.content }}</div>
    <div class="columns">
      <div class="column">
        <CardBar></CardBar>
      </div>
    </div>
  </div>
</template>

<script>

import CardBar from "@/views/card/CardBar"


export default {
  name: "Home",
  components: {CardBar},
  data() {
    return {
      billboard: {
        content: "",
      },
    };
  },
  created() {
  
  },
  methods: {
    
  },
};
</script>

