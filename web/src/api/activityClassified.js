import request from "@/utils/request";

export function pageQuery(page, limit, data) {
  return request({
    url: `/activity-classified/pageQuery/${page}/${limit}`,
    method: 'post',
    data: data
  })
}

export function deleteById(id) {
  return request({
    url: `/activity-classified/deleteById/${id}`,
    method: 'delete'
  })
}

export function insert(data) {
  return request({
    url: `/activity-classified/insert`,
    method: 'post',
    data: data
  })
}

export function update(data) {
  return request({
    url: `/activity-classified/update`,
    method: 'put',
    data: data
  })
}

export function getClassifiedAll() {
  return request({
    url: `/activity-classified/getClassifiedAll`,
    method: 'get'
  })
}
